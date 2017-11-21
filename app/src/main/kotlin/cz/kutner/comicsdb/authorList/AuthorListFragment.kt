package cz.kutner.comicsdb.authorList

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.di.NetworkModule
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.utils.ItemDiffCallback
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_progress.*
import org.koin.android.ext.android.inject
import pl.aprilapps.switcher.Switcher
import java.text.Normalizer

class AuthorListFragment : Fragment() {
    var loading = true
    var data: MutableList<Author> = ArrayList()
    var pastVisibleItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    lateinit var adapter: AuthorListAdapter
    var preloadCount: Int = 20
    val switcher: Switcher by lazy { Switcher.Builder(context!!).addContentView(content).addEmptyView(empty_view).addProgressView(progress_view).addErrorView(error_view).build() }
    val model by lazy { ViewModelProviders.of(this).get(AuthorListViewModel::class.java) }

    private val firebase by inject<FirebaseAnalytics>()
    private val networkModule by inject<NetworkModule>()

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

    private fun setupRecyclerView(view: View) {
        val llm = LinearLayoutManager(view.context)
        adapter = AuthorListAdapter(layoutInflater, data)
        recycler_view.layoutManager = llm
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                visibleItemCount = llm.childCount
                totalItemCount = llm.itemCount
                pastVisibleItems = llm.findFirstVisibleItemPosition()
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount - preloadCount && !loading) {
                    loading = true
                    model.loadAuthors()
                }
            }
        })
    }

    private fun setupTitleAndSearchText() {
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
            model.searchText = Normalizer.normalize(args.getString(SearchManager.QUERY), Normalizer.Form.NFD).replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Autoři"
            firebase.logVisit(contentName = "Zobrazení seznamu autorů", contentType = "Autor")
        }
    }

    private fun checkConnectionAndLoadData() {
        if (!networkModule.isConnected()) {
            switcher.showErrorView()
        } else {
            switcher.showProgressView()
            model.getAuthors().observe(this, Observer<List<Author>?> { newList ->
                val oldData = adapter.items
                adapter.items = newList
                val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(oldData, adapter.items))
                diffResult.dispatchUpdatesTo(adapter)
                loading = false
                switcher.showContentView()
            })
        }
    }

    companion object {
        fun newInstance(): AuthorListFragment {
            val args = Bundle()
            val fragment = AuthorListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}