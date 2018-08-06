package cz.kutner.comicsdb.comicsList

import android.app.SearchManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.kutner.comicsdb.abstracts.AbstractFragment
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.utils.logVisit
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.Normalizer

class ComicsListFragment : AbstractFragment<Comics>() {
    override val model: ComicsListViewModel by viewModel()
    override val adapter by lazy { ComicsListAdapter(layoutInflater, data) }

    override fun setupTitleAndSearchText() {
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" +
                    args.getString(SearchManager.QUERY) + "\""
            model.searchText =
                    Normalizer.normalize(args.getString(SearchManager.QUERY), Normalizer.Form.NFD)
                        .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
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


