package cz.kutner.comicsdb.seriesList

import android.app.SearchManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.kutner.comicsdb.abstracts.AbstractFragment
import cz.kutner.comicsdb.model.Series
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.Normalizer

class SeriesListFragment : AbstractFragment<Series>() {
    override val model: SeriesListViewModel by viewModel()
    override val adapter by lazy { SeriesListAdapter(layoutInflater, data) }

    override fun setupTitleAndSearchText() {
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" +
                    args.getString(SearchManager.QUERY) + "\""
            model.searchText =
                    Normalizer.normalize(args.getString(SearchManager.QUERY), Normalizer.Form.NFD)
                        .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Serie"
        }
    }

    companion object {
        fun newInstance(): SeriesListFragment {
            val args = Bundle()
            val fragment = SeriesListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


