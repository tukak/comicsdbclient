package cz.kutner.comicsdb.activity

import android.os.Bundle
import com.google.firebase.perf.metrics.AddTrace
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.SeriesDetailAdapter
import cz.kutner.comicsdb.model.SeriesDetail
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*

class SeriesDetailActivity : AbstractDetailActivity<SeriesDetail>() {

    override val prefix: String by lazy { getString(R.string.url_series_detail) }
    override val extraName = MainActivity.SERIES_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @AddTrace(name = "SeriesDetailLoadData")
    override fun loadData() {
        runAsync(retrofitModule.seriesDetailService.seriesDetail(id))
    }

    override fun showData() {
        supportActionBar?.title = result.name
        val adapter = SeriesDetailAdapter(this, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(contentName = "Zobrazení detailu série", contentType = "Série", contentId = result.name)
    }

}
