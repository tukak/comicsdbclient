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


public class SeriesDetailFragment : AbstractDetailFragment() {

    private var series: Series? = null

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
        if (series != null) {
            var existing_series: Series = series as Series
            (activity as AppCompatActivity).supportActionBar?.title = existing_series.name
            val adapter = SeriesDetailRVAdapter(existing_series)
            recycler_view.adapter = adapter
            recycler_view.setHasFixedSize(true)
            switcher.showContentView()
            Utils.logVisitToGoogleAnalytics(screenName = "SeriesDetailFragment", category = "Detail", action = existing_series.name)
            Utils.logVisitToFabricAnswers(contentName = "Zobrazení detailu série", contentType = "Série", contentId = existing_series.name)
        }
    }

    companion object {
        public fun newInstance(): SeriesDetailFragment {
            val args = Bundle()
            val fragment = SeriesDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
