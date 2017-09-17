package cz.kutner.comicsdb.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.inject
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.metalab.asyncawait.RetrofitHttpError
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.di.NetworkModule
import cz.kutner.comicsdb.di.RetrofitModule
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_progress.*
import pl.aprilapps.switcher.Switcher
import retrofit2.Call
import timber.log.Timber
import java.util.*

abstract class AbstractFragment<Item : Any> : Fragment() {
    var lastPage: Int = 0
    var searchRunning: Boolean = false
    var loading: Boolean = false
    var lastItem: Item? = null
    var data: MutableList<Item> = ArrayList()
    var result: List<Item> = ArrayList()
    var pastVisibleItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    var preloadCount: Int = 20
    val switcher: Switcher by lazy { Switcher.Builder(activity).addContentView(content).addEmptyView(empty_view).addProgressView(progress_view).addErrorView(error_view).build() }

    val retrofitModule by inject<RetrofitModule>()
    val firebase by inject<FirebaseAnalytics>()
    private val networkModule by inject<NetworkModule>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val llm = LinearLayoutManager(view?.context)
        recycler_view.layoutManager = llm
        recycler_view.adapter = adapter
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
                        loadData()
                    }
                }
            }
        })

        checkConnectionAndLoadData()
    }

    abstract fun loadData()

    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    private fun checkConnectionAndLoadData() {
        if (!isConnected()) {
            switcher.showErrorView()
        } else {
            switcher.showProgressView()
            loadData()
        }
    }

    open fun showData() {
        if (activity?.isFinishing == false) {
            searchRunning = false
            if (lastItem == null) {
                switcher.showContentView()
            }
            if (result.isNotEmpty()) {
                if (lastItem == null || lastItem != result[0]) {
                    lastItem = result[0]
                    data.addAll(result)
                }
            }
            adapter.notifyItemRangeInserted(lastPage * preloadCount, preloadCount)
            loading = false
        }
    }

    fun runAsync(call: Call<List<Item>>) {
        async {
            try {
                result = awaitSuccessful(call)
            } catch (e: RetrofitHttpError) {
                switcher.showErrorView()
                Timber.e(e)
            }
            showData()
            lastPage++
        }
    }
}