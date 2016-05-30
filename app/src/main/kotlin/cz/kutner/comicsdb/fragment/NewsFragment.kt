package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.adapter.NewsListAdapter
import cz.kutner.comicsdb.connector.service.NewsService
import cz.kutner.comicsdb.di.Tracker
import cz.kutner.comicsdb.model.NewsItem
import cz.kutner.comicsdb.utils.Utils
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class NewsFragment : AbstractFragment<NewsItem>() {

    @Inject lateinit var newsService: NewsService
    @Inject lateinit var tracker: Tracker

    init {
        preloadCount = 0
        endless = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as ComicsDBApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = NewsListAdapter(activity, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            async() {
                result = newsService.listNews().execute().body()
                uiThread {
                    showData()
                    lastPage++
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Novinky"
        tracker.logVisit(screenName = "NewsFragment")
        Utils.logVisitToFabricAnswers(contentName = "Zobrazení novinek", contentType = "Novinky")
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


