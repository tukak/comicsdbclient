package cz.kutner.comicsdb.newsList

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.abstracts.AbstractFragment
import cz.kutner.comicsdb.model.NewsItem
import cz.kutner.comicsdb.utils.logVisit

class NewsListFragment : AbstractFragment<NewsItem>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = NewsListAdapter(inflater, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val call = retrofitModule.newsListService.listNews(lastPage * preloadCount, preloadCount)
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Novinky"
        firebase.logVisit(contentName = "Zobrazení novinek", contentType = "Novinky")
    }

    companion object {

        fun newInstance(): NewsListFragment {

            val args = Bundle()

            val fragment = NewsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


