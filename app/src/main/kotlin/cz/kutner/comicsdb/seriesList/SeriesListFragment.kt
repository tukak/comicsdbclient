package cz.kutner.comicsdb.seriesList

import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.kutner.comicsdb.abstracts.AbstractFragment
import cz.kutner.comicsdb.model.Series
import cz.kutner.comicsdb.utils.logVisit
import java.text.Normalizer

class SeriesListFragment : AbstractFragment<Series>() {

    override fun setupRecyclerView(view: View) {
        model = ViewModelProviders.of(this).get(SeriesListViewModel::class.java)
        adapter = SeriesListAdapter(layoutInflater, data)
        super.setupRecyclerView(view)
    }

    override fun setupTitleAndSearchText() {
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
            model.searchText = Normalizer.normalize(args.getString(SearchManager.QUERY), Normalizer.Form.NFD).replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Serie"
            firebase.logVisit(contentName = "Zobrazení seznamu sérií", contentType = "Série")
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


