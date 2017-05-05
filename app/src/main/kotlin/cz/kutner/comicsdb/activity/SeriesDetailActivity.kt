package cz.kutner.comicsdb.activity

import android.os.Bundle
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.SeriesDetailAdapter
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Series
import cz.kutner.comicsdb.utils.Utils
import kotlinx.android.synthetic.main.fragment_list.*
import space.traversal.kapsule.Injects

class SeriesDetailActivity : AbstractDetailActivity<Series>(), Injects<RetrofitModule> {

    override val prefix: String by lazy { getString(R.string.url_series_detail) }
    override val extraName = MainActivity.SERIES_ID

    private val seriesDetailService by required { seriesDetailService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject((application as ComicsDBApplication).retrofitModule)
    }

    override fun loadData() {
        runAsync(seriesDetailService.seriesDetail(id))
    }

    override fun showData() {
        supportActionBar?.title = result.name
        val adapter = SeriesDetailAdapter(this, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        Utils.logVisit(contentName = "Zobrazení detailu série", contentType = "Série", contentId = result.name)
    }

}
