package cz.kutner.comicsdb.forumList

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.metalab.asyncawait.RetrofitHttpError
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import cz.kutner.comicsdb.abstracts.AbstractFragmentSpinner
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.utils.logVisit
import timber.log.Timber

class ForumListFragment : AbstractFragmentSpinner<ForumEntry>() {

    private fun fillSpinnerValues() {
        val call = retrofitModule.forumListService.getForumList()
        async {
            try {
                spinnerValues = arrayOf(Filter(0, "Všechna fora")).plus(awaitSuccessful(call))
            } catch (e: RetrofitHttpError) {
                switcher.showErrorView()
                Timber.e(e)
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ForumListAdapter(inflater, data)
        fillSpinnerValues()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val call = retrofitModule.forumListService.filteredForumList(lastPage * preloadCount, preloadCount, filter)
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
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
