package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.adapter.NewsListAdapter
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.NewsItem
import cz.kutner.comicsdb.utils.Utils
import space.traversal.kapsule.Injects

class NewsFragment : AbstractFragment<NewsItem>(), Injects<RetrofitModule> {

    private val newsService by required{ newsService }

    init {
        preloadCount = 0
        endless = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject((activity.application as ComicsDBApplication).retrofitModule)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = NewsListAdapter(activity, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val call = newsService.listNews()
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Novinky"
        Utils.logVisit(contentName = "Zobrazení novinek", contentType = "Novinky")
    }

    companion object {

        fun newInstance(): NewsFragment {

            val args = Bundle()

            val fragment = NewsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


