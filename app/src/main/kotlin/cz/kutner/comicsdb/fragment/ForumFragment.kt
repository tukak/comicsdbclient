package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.adapter.ForumListAdapter
import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.utils.logVisit

class ForumFragment : AbstractFragment<ForumEntry>() {

    init {
        preloadCount = 20
        spinnerEnabled = true
        spinnerValues = arrayOf("Všechna fora", "* Připomínky a návrhy", "Fabula Rasa", "Filmový klub", "Pindárna", "Povinná četba", "Poznej comics nebo postavu", "Sběratelský klub", "Slevy, výprodeje, bazary", "Srazy, cony, festivaly", "Stripy, jouky, fejky :)")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ForumListAdapter(activity, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val call = retrofitModule.forumService.filteredForumList(lastPage*preloadCount, preloadCount)
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Forum"
        firebase.logVisit(contentName = "Zobrazení fór", contentType = "Fórum")
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
