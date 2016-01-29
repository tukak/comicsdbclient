package cz.kutner.comicsdb.fragment

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.holder.ComicsViewHolder
import cz.kutner.comicsdb.model.Comics
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import uk.co.ribot.easyadapter.EasyRecyclerAdapter
import java.text.Normalizer

public class ComicsListFragment : AbstractFragment<Comics>(), AnkoLogger {


    init {
        preloadCount = 20
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = EasyRecyclerAdapter(
                context,
                ComicsViewHolder::class.java,
                data as List<Any>?)
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
                    result = ComicsDBApplication.comicsListService.comicsSearch(searchText)
                    uiThread {
                        showData()
                        lastPage++
                    }
                }
                endless = false
            } else {
                //zobrazujeme nejnovější
                async() {
                    result = ComicsDBApplication.comicsListService.comicsList(lastPage)
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
            Utils.logVisitToGoogleAnalytics(screenName = "ComicsListFragment - Search")
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Comicsy"
            Utils.logVisitToGoogleAnalytics(screenName = "ComicsListFragment - List")
            Utils.logVisitToFabricAnswers(contentName = "Zobrazení seznamu comicsů", contentType = "Comics")
        }
    }

    companion object {

        public fun newInstance(): ComicsListFragment {

            val args = Bundle()

            val fragment = ComicsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


