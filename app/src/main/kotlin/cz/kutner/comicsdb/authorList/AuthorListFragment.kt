package cz.kutner.comicsdb.authorList

import android.app.SearchManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.kutner.comicsdb.abstracts.AbstractFragment
import cz.kutner.comicsdb.model.Author
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.Normalizer

class AuthorListFragment : AbstractFragment<Author>() {
    override val model: AuthorListViewModel by viewModel()
    override val adapter by lazy { AuthorListAdapter(layoutInflater, data) }

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