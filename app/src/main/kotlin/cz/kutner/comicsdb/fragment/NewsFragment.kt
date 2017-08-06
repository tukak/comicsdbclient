package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.perf.metrics.AddTrace
import cz.kutner.comicsdb.adapter.NewsListAdapter
import cz.kutner.comicsdb.model.NewsItem
import cz.kutner.comicsdb.utils.logVisit

class NewsFragment : AbstractFragment<NewsItem>() {

    init {
        preloadCount = 0
        endless = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = NewsListAdapter(activity, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @AddTrace(name = "NewsLoadData")
    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val call = retrofitModule.newsService.listNews()
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Novinky"
        firebase.logVisit(contentName = "Zobrazení novinek", contentType = "Novinky")
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


