package cz.kutner.comicsdb.activity

import android.os.Bundle
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.ComicsDetailAdapter
import cz.kutner.comicsdb.connector.service.ComicsService
import cz.kutner.comicsdb.di.Tracker
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.utils.Utils
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class ComicsDetailActivity : AbstractDetailActivity() {

    override val prefix by lazy { getString(R.string.url_comics_detail) }
    override val extraName = MainActivity.COMICS_ID

    @Inject lateinit var comicsDetailService: ComicsService
    @Inject lateinit var tracker: Tracker

    private lateinit var comics: Comics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as ComicsDBApplication).applicationComponent.inject(this)
    }

    override fun loadData() {
        async() {
            comics = comicsDetailService.getComics(id)
            uiThread {
                showData()
            }
        }
    }

    override fun showData() {
        supportActionBar?.title = comics.name
        val adapter = ComicsDetailAdapter(this, listOf(comics) + comics.comments)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        tracker.logVisit(screenName = "ComicsDetailFragment", category = "Detail", action = comics.name)
        Utils.logVisitToFabricAnswers(contentName = "Zobrazení detailu komiksu", contentType = "Comics", contentId = comics.name)
    }
}
