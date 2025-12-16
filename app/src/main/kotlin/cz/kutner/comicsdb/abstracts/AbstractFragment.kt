package cz.kutner.comicsdb.abstracts

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.databinding.FragmentListBinding
import cz.kutner.comicsdb.network.NetworkModule
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.utils.ItemDiffCallback
import io.americanexpress.busybee.BusyBee
import org.koin.android.ext.android.inject
import cz.kutner.comicsdb.utils.ViewStateSwitcher
import timber.log.Timber

abstract class AbstractFragment<Data : Item> : Fragment() {
    private val busyBee = BusyBee.singleton()
    val data: MutableList<Data> = ArrayList()
    abstract val adapter: AbstractListAdapter
    abstract val model: AbstractPagedViewModel<Data>
    var loading = true
    var firstLoad = true
    var pastVisibleItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var preloadCount: Int = 20

    private var _binding: FragmentListBinding? = null
    protected val binding get() = _binding!!

    protected open lateinit var switcher: ViewStateSwitcher
    protected open val recyclerView: RecyclerView get() = binding.recyclerView

    protected val networkModule by inject<NetworkModule>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::switcher.isInitialized) {
            switcher = ViewStateSwitcher.Builder(requireContext())
                .addContentView(binding.content)
                .addEmptyView(binding.viewEmptyInclude.root)
                .addProgressView(binding.viewProgressInclude.root)
                .addErrorView(binding.viewErrorInclude.root)
                .build()
        }
        setupTryAgainButton()
        setupRecyclerView(view)
        setupTitleAndSearchText()
        checkConnectionAndLoadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected open fun setupTryAgainButton() {
        binding.viewErrorInclude.tryAgain.setOnClickListener {
            if (networkModule.isConnected()) {
                checkConnectionAndLoadData()
            }
        }
    }

    open fun setupRecyclerView(view: View) {
        val llm = LinearLayoutManager(view.context)
        recyclerView.layoutManager = llm
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                visibleItemCount = llm.childCount
                totalItemCount = llm.itemCount
                pastVisibleItems = llm.findFirstVisibleItemPosition()
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount - preloadCount && !loading && !firstLoad) {
                    loading = true
                    model.loadData()
                }
            }
        })
    }

    abstract fun setupTitleAndSearchText()

    protected fun checkConnectionAndLoadData() {
        if (!networkModule.isConnected()) {
            switcher.showErrorView()
        } else {
            switcher.showProgressView()
            busyBee.busyWith(this::class.java.name)
            model.getData().observe(viewLifecycleOwner, Observer<List<Data>?> { newList ->
                try {
                    if (newList != null) {
                        val oldData = adapter.items ?: emptyList()
                        adapter.items = newList
                        val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(oldData, newList))
                        diffResult.dispatchUpdatesTo(adapter)
                        loading = false
                        if (firstLoad) {
                            switcher.showContentView()
                            firstLoad = false
                        }
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                } finally {
                    busyBee.completed(this::class.java.name)
                }
            })
        }
    }
}