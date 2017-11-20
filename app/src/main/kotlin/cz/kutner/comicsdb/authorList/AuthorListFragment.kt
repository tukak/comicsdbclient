package cz.kutner.comicsdb.authorList

import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
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
import timber.log.Timber
import java.text.Normalizer
import java.util.*

class AuthorListFragment : Fragment() {
    var lastPage: Int = 0
    var searchRunning: Boolean = false
    var loading: Boolean = false
    var lastItem: Author? = null
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
        val llm = LinearLayoutManager(view.context)
        adapter = AuthorListAdapter(layoutInflater, data)
        recycler_view.layoutManager = llm
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        try_again.setOnClickListener {
            if (networkModule.isConnected()) {
                checkConnectionAndLoadData()
            }
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                visibleItemCount = llm.childCount
                totalItemCount = llm.itemCount
                pastVisibleItems = llm.findFirstVisibleItemPosition()
                if (!loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount - preloadCount) {
                        loading = true
                        Timber.i("Getting new data, because $visibleItemCount + $pastVisibleItems >= $totalItemCount - $preloadCount")
                        model.loadMoreAuthors("") //TODO fix - searchText do vm?
                        loadData()
                        loading = false
                    }
                    else {
                        Timber.i("Not new data, because $visibleItemCount + $pastVisibleItems < $totalItemCount - $preloadCount")
                    }
                }
            }
        })

        checkConnectionAndLoadData()
    }

    private fun checkConnectionAndLoadData() {
        if (!networkModule.isConnected()) {
            switcher.showErrorView()
        } else {
            switcher.showProgressView()
            loadData()
        }
    }

    fun loadData() {
        val args = this.arguments
        var searchText = ""
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            searchText = args.getString(SearchManager.QUERY)
            searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
        }
        val oldData = adapter.items
        Timber.i("Old data ${oldData.size}")
        adapter.items = model.getAuthors(searchText)
        Timber.i("New data ${adapter.items.size}")
        adapter.updateList(oldData)
        Timber.i("New new data ${adapter.items.size}")
        switcher.showContentView()
    }

    override fun onStart() {
        super.onStart()
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Autoři"
            firebase.logVisit(contentName = "Zobrazení seznamu autorů", contentType = "Autor")
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


