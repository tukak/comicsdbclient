package cz.kutner.comicsdb.fragment

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.holder.AuthorViewHolder
import cz.kutner.comicsdb.model.Author
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import uk.co.ribot.easyadapter.EasyRecyclerAdapter
import java.text.Normalizer

class AuthorFragment : AbstractFragment<Author>() {

    init {
        preloadCount = 20
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = EasyRecyclerAdapter(
                context,
                AuthorViewHolder::class.java,
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
                    result = ComicsDBApplication.authorService.authorSearch(searchText)
                    uiThread {
                        showData()
                        lastPage++
                    }
                }
                endless = false
            } else {
                //zobrazujeme nejnovější
                async() {
                    result = ComicsDBApplication.authorService.listAuthors(lastPage)
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
            Utils.logVisitToGoogleAnalytics(screenName = "AuthorFragment - Search")
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Autoři"
            Utils.logVisitToGoogleAnalytics(screenName = "AuthorFragment - List")
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


