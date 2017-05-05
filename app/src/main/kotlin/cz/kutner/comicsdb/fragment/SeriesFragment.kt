package cz.kutner.comicsdb.fragment

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.adapter.SeriesListAdapter
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Series
import cz.kutner.comicsdb.utils.Utils
import space.traversal.kapsule.Injects
import java.text.Normalizer

class SeriesFragment : AbstractFragment<Series>(), Injects<RetrofitModule> {

    private val seriesListService by required { seriesListService }

    init {
        preloadCount = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject((activity.application as ComicsDBApplication).retrofitModule)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = SeriesListAdapter(activity, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val args = this.arguments
            if (args != null && args.containsKey(SearchManager.QUERY)) {
                //neco vyhledavame
                var searchText: String = args.getString(SearchManager.QUERY)
                searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
                val call = seriesListService.searchSeries(searchText)
                runAsync(call)
                endless = false
            } else {
                //zobrazujeme nejnovější
                val call = seriesListService.getSeriesList(lastPage)
                runAsync(call)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Serie"
            Utils.logVisit(contentName = "Zobrazení seznamu sérií", contentType = "Série")
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


