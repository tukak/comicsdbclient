package cz.kutner.comicsdb.fragment

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.adapter.AuthorListAdapter
import cz.kutner.comicsdb.connector.service.AuthorListService
import cz.kutner.comicsdb.di.Tracker
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.utils.Utils
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.text.Normalizer
import javax.inject.Inject

class AuthorFragment : AbstractFragment<Author>() {

    @Inject lateinit var authorListService: AuthorListService
    @Inject lateinit var tracker: Tracker

    init {
        preloadCount = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as ComicsDBApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = AuthorListAdapter(activity, data)
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
                async() {
                    result = authorListService.authorSearch(searchText).execute().body()
                    uiThread {
                        showData()
                        lastPage++
                    }
                }
                endless = false
            } else {
                //zobrazujeme nejnovější
                async() {
                    result = authorListService.listAuthors(lastPage).execute().body()
                    uiThread {
                        showData()
                        lastPage++
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
            tracker.logVisit(screenName = "AuthorFragment - Search")
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Autoři"
            tracker.logVisit(screenName = "AuthorFragment - List")
            Utils.logVisitToFabricAnswers(contentName = "Zobrazení seznamu autorů", contentType = "Autor")
        }
    }

    companion object {

        fun newInstance(): AuthorFragment {

            val args = Bundle()

            val fragment = AuthorFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


