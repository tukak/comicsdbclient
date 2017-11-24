package cz.kutner.comicsdb.forumList

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.kutner.comicsdb.abstracts.AbstractFragmentSpinner
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.utils.logVisit

class ForumListFragment : AbstractFragmentSpinner<ForumEntry>() {
    init {
        spinnerValues = arrayOf(Filter(0, "Všechna fora"),
                Filter(1, "* Připomínky a návrhy"),
                Filter(5, "Pindárna"),
                Filter(13, "Art"))
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

    /*private fun fillSpinnerValues() {
        val call = retrofitModule.forumListService.getForumList()
        async {
            try {
                spinnerValues = arrayOf(Filter(0, "Všechna fora")).plus(awaitSuccessful(call))
            } catch (e: RetrofitHttpError) {
                switcher.showErrorView()
                Timber.e(e)
            }

        }
    } */

    companion object {
        fun newInstance(): ForumListFragment {
            val args = Bundle()
            val fragment = ForumListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
