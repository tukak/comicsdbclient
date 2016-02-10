package cz.kutner.comicsdb.fragment


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.adapter.SeriesDetailRVAdapter
import cz.kutner.comicsdb.model.Series
import kotlinx.android.synthetic.main.fragment.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread


class SeriesDetailFragment : AbstractDetailFragment() {

    private lateinit var series: Series

    override fun loadData() {
        val id = this.arguments.getInt("id")
        async() {
            series = ComicsDBApplication.seriesDetailService.seriesDetail(id)
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
