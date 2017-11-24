package cz.kutner.comicsdb.newsList

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.kutner.comicsdb.abstracts.AbstractFragment
import cz.kutner.comicsdb.model.NewsItem
import cz.kutner.comicsdb.utils.logVisit

class NewsListFragment : AbstractFragment<NewsItem>() {

    override fun setupRecyclerView(view: View) {
        model = ViewModelProviders.of(this).get(NewsListViewModel::class.java)
        adapter = NewsListAdapter(layoutInflater, data)
        super.setupRecyclerView(view)
    }

    override fun setupTitleAndSearchText() {
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


