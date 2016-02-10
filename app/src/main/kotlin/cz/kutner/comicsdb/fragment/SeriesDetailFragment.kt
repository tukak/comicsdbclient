package cz.kutner.comicsdb.fragment


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.adapter.SeriesDetailRVAdapter
import cz.kutner.comicsdb.connector.service.SeriesDetailService
import cz.kutner.comicsdb.model.Series
import kotlinx.android.synthetic.main.fragment.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import javax.inject.Inject


class SeriesDetailFragment : AbstractDetailFragment() {

    @Inject lateinit var seriesDetailService: SeriesDetailService

    private lateinit var series: Series

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as ComicsDBApplication).retrofitComponent.inject(this)
    }

    override fun loadData() {
        val id = this.arguments.getInt("id")
        async() {
            series = seriesDetailService.seriesDetail(id)
            uiThread {
                showData()
            }
        }
    }

    override fun showData() {
        (activity as AppCompatActivity).supportActionBar?.title = series.name
        val adapter = SeriesDetailRVAdapter(series)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        Utils.logVisitToGoogleAnalytics(screenName = "SeriesDetailFragment", category = "Detail", action = series.name)
        Utils.logVisitToFabricAnswers(contentName = "Zobrazení detailu série", contentType = "Série", contentId = series.name)
    }

    companion object {
        fun newInstance(): SeriesDetailFragment {
            val args = Bundle()
            val fragment = SeriesDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
