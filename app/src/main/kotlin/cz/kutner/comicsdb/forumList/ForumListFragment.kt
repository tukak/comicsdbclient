package cz.kutner.comicsdb.forumList

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.kutner.comicsdb.abstracts.AbstractFragmentSpinner
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.utils.logVisit
import org.koin.android.ext.android.inject

class ForumListFragment : AbstractFragmentSpinner<ForumEntry>() {
    val retrofitModule by inject<RetrofitModule>()

    init {
        spinnerValues = arrayOf(Filter(0, "Všechna fora"),
                Filter(1, "* Připomínky a návrhy"),
                Filter(13, "Art"),
                Filter(10, "Fabula Rasa"),
                Filter(5, "Filmový klub"),
                Filter(3, "Pindárna"),
                Filter(4, "Povinná četba"),
                Filter(9, "Poznej comics nebo postavu"),
                Filter(12, "Publicistika"),
                Filter(6, "Sběratelský klub"),
                Filter(11, "Slevy, výprodeje, bazary"),
                Filter(8, "Srazy, cony, festivaly"),
                Filter(7, "Stripy, jouky, fejky :)"))
    }

    override fun setupRecyclerView(view: View) {
        model = ViewModelProviders.of(this).get(ForumListViewModel::class.java)
        adapter = ForumListAdapter(layoutInflater, data)
        super.setupRecyclerView(view)
    }

    override fun setupTitleAndSearchText() {
        (activity as AppCompatActivity).supportActionBar?.title = "Forum"
        firebase.logVisit(contentName = "Zobrazení fór", contentType = "Fórum")
    }

    companion object {
        fun newInstance(): ForumListFragment {
            val args = Bundle()
            val fragment = ForumListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
