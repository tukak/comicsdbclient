package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.connector.helper.ForumHelper
import cz.kutner.comicsdb.connector.service.ForumService
import cz.kutner.comicsdb.holder.ForumViewHolder
import cz.kutner.comicsdb.model.ForumEntry
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import uk.co.ribot.easyadapter.EasyRecyclerAdapter
import javax.inject.Inject

class ForumFragment : AbstractFragment<ForumEntry>() {

    @Inject lateinit var forumService: ForumService

    init {
        preloadCount = 20
        spinnerEnabled = true
        spinnerValues = arrayOf("Všechna fora", "* Připomínky a návrhy", "Fabula Rasa", "Filmový klub", "Pindárna", "Povinná četba", "Poznej comics nebo postavu", "Sběratelský klub", "Slevy, výprodeje, bazary", "Srazy, cony, festivaly", "Stripy, jouky, fejky :)")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as ComicsDBApplication).retrofitComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = EasyRecyclerAdapter(
                context,
                ForumViewHolder::class.java,
                data as List<ForumEntry>)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val searchText = ""
            async() {
                result = forumService.filteredForumList(lastPage, ForumHelper.getForumId(filter), searchText)
                uiThread {
                    showData()
                    lastPage++
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Forum"
        Utils.logVisitToGoogleAnalytics(screenName = "ForumFragment")
        Utils.logVisitToFabricAnswers(contentName = "Zobrazení fór", contentType = "Fórum")
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
