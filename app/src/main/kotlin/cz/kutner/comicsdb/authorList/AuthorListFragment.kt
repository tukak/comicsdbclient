package cz.kutner.comicsdb.authorList

import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.kutner.comicsdb.abstracts.AbstractFragment
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.utils.logVisit
import java.text.Normalizer

class AuthorListFragment : AbstractFragment<Author>() {

    override fun setupRecyclerView(view: View) {
        model = ViewModelProviders.of(this).get(AuthorListViewModel::class.java)
        adapter = AuthorListAdapter(layoutInflater, data)
        super.setupRecyclerView(view)
    }

    override fun setupTitleAndSearchText() {
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" +
                    args.getString(SearchManager.QUERY) + "\""
            model.searchText =
                    Normalizer.normalize(args.getString(SearchManager.QUERY), Normalizer.Form.NFD)
                        .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
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