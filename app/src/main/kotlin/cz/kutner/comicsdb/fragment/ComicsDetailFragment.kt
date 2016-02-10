package cz.kutner.comicsdb.fragment


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.adapter.ComicsDetailRVAdapter
import cz.kutner.comicsdb.connector.service.ComicsService
import cz.kutner.comicsdb.model.Comics
import kotlinx.android.synthetic.main.fragment.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class ComicsDetailFragment : AbstractDetailFragment() {

    @Inject lateinit var comicsDetailService: ComicsService

    private lateinit var comics: Comics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as ComicsDBApplication).retrofitComponent.inject(this)
    }

    override fun loadData() {
        val id = this.arguments.getInt("id")
        async() {
            comics = comicsDetailService.getComics(id)
            uiThread {
                showData()
            }
        }
    }

    override fun showData() {
        (activity as AppCompatActivity).supportActionBar?.title = comics.name
        val adapter = ComicsDetailRVAdapter(comics)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        Utils.logVisitToGoogleAnalytics(screenName = "ComicsDetailFragment", category = "Detail", action = comics.name)
        Utils.logVisitToFabricAnswers(contentName = "Zobrazení detailu komiksu", contentType = "Comics", contentId = comics.name)
    }

    companion object {
        fun newInstance(): ComicsDetailFragment {
            val args = Bundle()
            val fragment = ComicsDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
