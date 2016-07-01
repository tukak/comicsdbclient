package cz.kutner.comicsdb.activity

import android.os.Bundle
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.SeriesDetailAdapter
import cz.kutner.comicsdb.connector.service.SeriesDetailService
import cz.kutner.comicsdb.di.Tracker
import cz.kutner.comicsdb.model.Series
import cz.kutner.comicsdb.utils.Utils
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class SeriesDetailActivity : AbstractDetailActivity() {

    override val prefix: String by lazy { getString(R.string.url_series_detail) }
    override val extraName = MainActivity.SERIES_ID

    @Inject lateinit var seriesDetailService: SeriesDetailService
    @Inject lateinit var tracker: Tracker

    private lateinit var series: Series

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as ComicsDBApplication).applicationComponent.inject(this)
    }

    override fun loadData() {
        doAsync() {
            series = seriesDetailService.seriesDetail(id).execute().body()
            uiThread {
                showData()
            }
        }
    }

    override fun showData() {
        supportActionBar?.title = series.name
        val adapter = SeriesDetailAdapter(this, listOf(series) + series.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        tracker.logVisit(screenName = "SeriesDetailFragment", category = "Detail", action = series.name)
        Utils.logVisitToFabricAnswers(contentName = "Zobrazení detailu série", contentType = "Série", contentId = series.name)
    }

}
