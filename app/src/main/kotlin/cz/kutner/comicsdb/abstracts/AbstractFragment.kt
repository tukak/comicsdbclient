package cz.kutner.comicsdb.abstracts

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.di.NetworkModule
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.utils.ItemDiffCallback
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_progress.*
import org.koin.android.ext.android.inject
import pl.aprilapps.switcher.Switcher
import timber.log.Timber

abstract class AbstractFragment<Data : Item> : Fragment() {
    var data: MutableList<Data> = ArrayList()
    lateinit var adapter: AbstractListAdapter
    lateinit var model: AbstractAndroidViewModel<Data>
    var loading = true
    var firstLoad = true
    var pastVisibleItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var preloadCount: Int = 20
    val switcher: Switcher by lazy { Switcher.Builder(context!!).addContentView(content).addEmptyView(empty_view).addProgressView(progress_view).addErrorView(error_view).build() }

    private val networkModule by inject<NetworkModule>()
    val firebase by inject<FirebaseAnalytics>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTryAgainButton()
        setupRecyclerView(view)
        setupTitleAndSearchText()
        checkConnectionAndLoadData()
    }

    private fun setupTryAgainButton() {
        try_again.setOnClickListener {
            if (networkModule.isConnected()) {
                checkConnectionAndLoadData()
            }
        }
    }

    open fun setupRecyclerView(view: View) {
        val llm = LinearLayoutManager(view.context)
        recycler_view.layoutManager = llm
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
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

    private fun checkConnectionAndLoadData() {
        if (!networkModule.isConnected()) {
            switcher.showErrorView()
        } else {
            switcher.showProgressView()
            model.getData().observe(this, Observer<List<Data>?> { newList ->
                Timber.i("Got ${newList?.size} records")
                val oldData = adapter.items
                adapter.items = newList
                val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(oldData, adapter.items))
                diffResult.dispatchUpdatesTo(adapter)
                loading = false
                if (firstLoad) {
                    switcher.showContentView()
                    firstLoad = false
                }
            })
        }
    }
}