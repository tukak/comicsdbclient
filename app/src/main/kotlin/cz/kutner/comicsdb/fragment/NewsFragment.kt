package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.holder.NewsViewHolder
import cz.kutner.comicsdb.model.NewsItem
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import uk.co.ribot.easyadapter.EasyRecyclerAdapter

public class NewsFragment : AbstractFragment<NewsItem>() {


    init {
        preloadCount = 0
        endless = false
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = EasyRecyclerAdapter(
                context,
                NewsViewHolder::class.java,
                data as List<NewsItem>)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            async() {
                result = ComicsDBApplication.newsService.listNews()
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
        Utils.logVisitToGoogleAnalytics(screenName = "NewsFragment")
        Utils.logVisitToFabricAnswers(contentName = "Zobrazení novinek", contentType = "Novinky")
    }

    companion object {

        public fun newInstance(): NewsFragment {

            val args = Bundle()

            val fragment = NewsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


