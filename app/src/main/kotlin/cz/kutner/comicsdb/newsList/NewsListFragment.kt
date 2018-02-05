package cz.kutner.comicsdb.newsList

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.kutner.comicsdb.abstracts.AbstractFragment
import cz.kutner.comicsdb.model.NewsItem
import cz.kutner.comicsdb.utils.logVisit
import org.koin.android.architecture.ext.viewModel

class NewsListFragment : AbstractFragment<NewsItem>() {
    override val model: NewsListViewModel by viewModel()
    override val adapter by lazy { NewsListAdapter(layoutInflater, data) }

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


