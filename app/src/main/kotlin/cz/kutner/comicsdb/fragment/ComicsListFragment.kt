package cz.kutner.comicsdb.fragment

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.adapter.ComicsListAdapter
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.utils.logVisit
import java.text.Normalizer

class ComicsListFragment : AbstractFragment<Comics>() {

    init {
        preloadCount = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ComicsListAdapter(activity, data)
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
                val call = retrofitModule.comicsListService.comicsSearch(searchText)
                runAsync(call)
                endless = false
            } else {
                //zobrazujeme nejnovější
                val call = retrofitModule.comicsListService.comicsList(lastPage*20, 20)
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
            (activity as AppCompatActivity).supportActionBar?.title = "Comicsy"
            firebase.logVisit(contentName = "Zobrazení seznamu comicsů", contentType = "Comics")
        }
    }

    companion object {

        fun newInstance(): ComicsListFragment {

            val args = Bundle()

            val fragment = ComicsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


