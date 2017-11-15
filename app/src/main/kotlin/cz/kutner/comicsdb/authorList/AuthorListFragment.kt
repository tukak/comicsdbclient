package cz.kutner.comicsdb.authorList

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.abstracts.AbstractFragment
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.utils.logVisit
import java.text.Normalizer

class AuthorListFragment : AbstractFragment<Author>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = AuthorListAdapter(inflater, data)
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
            val call = retrofitModule.authorListService.listAuthors(lastPage * preloadCount, preloadCount, searchText)
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Autoři"
            firebase.logVisit(contentName = "Zobrazení seznamu autorů", contentType = "Autor")
        }
    }

    companion object {

        fun newInstance(): AuthorListFragment {

            val args = Bundle()

            val fragment = AuthorListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


