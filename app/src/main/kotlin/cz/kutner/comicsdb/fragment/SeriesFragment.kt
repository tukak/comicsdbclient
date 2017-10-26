package cz.kutner.comicsdb.fragment

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.adapter.SeriesListAdapter
import cz.kutner.comicsdb.model.Series
import cz.kutner.comicsdb.utils.logVisit
import java.text.Normalizer

class SeriesFragment : AbstractFragment<Series>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = SeriesListAdapter(inflater, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val args = this.arguments
            var searchText = ""
            if (args != null && args.containsKey(SearchManager.QUERY)) {
                searchText = args.getString(SearchManager.QUERY)
                searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
            }
            val call = retrofitModule.seriesListService.getSeriesList(lastPage * preloadCount, preloadCount, searchText)
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Serie"
            firebase.logVisit(contentName = "Zobrazení seznamu sérií", contentType = "Série")
        }
    }

    companion object {

        fun newInstance(): SeriesFragment {

            val args = Bundle()

            val fragment = SeriesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


