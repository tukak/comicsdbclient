package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.adapter.ForumListAdapter
import cz.kutner.comicsdb.connector.helper.ForumHelper
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.utils.Utils
import space.traversal.kapsule.Injects

class ForumFragment : AbstractFragment<ForumEntry>(), Injects<RetrofitModule> {

    private val forumService by required { forumService }

    init {
        preloadCount = 20
        spinnerEnabled = true
        spinnerValues = arrayOf("Všechna fora", "* Připomínky a návrhy", "Fabula Rasa", "Filmový klub", "Pindárna", "Povinná četba", "Poznej comics nebo postavu", "Sběratelský klub", "Slevy, výprodeje, bazary", "Srazy, cony, festivaly", "Stripy, jouky, fejky :)")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject((activity.application as ComicsDBApplication).retrofitModule)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ForumListAdapter(activity, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val searchText = ""
            val call = forumService.filteredForumList(lastPage, ForumHelper.getForumId(filter), searchText)
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Forum"
        Utils.logVisit(contentName = "Zobrazení fór", contentType = "Fórum")
    }

    companion object {

        fun newInstance(): ForumFragment {

            val args = Bundle()

            val fragment = ForumFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
