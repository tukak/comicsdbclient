package cz.kutner.comicsdb.seriesDetail

import android.arch.lifecycle.ViewModelProviders
import cz.kutner.comicsdb.activity.AbstractDetailActivity
import cz.kutner.comicsdb.model.SeriesDetail
import cz.kutner.comicsdb.utils.fromHtml
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*

class SeriesDetailActivity : AbstractDetailActivity<SeriesDetail>() {
    val model by lazy { ViewModelProviders.of(this).get(SeriesDetailViewModel::class.java) }

    override fun loadData() {
        result = model.getSeriesDetail(id)
        supportActionBar?.title = result.name.fromHtml()
        val adapter = SeriesDetailAdapter(layoutInflater, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(contentName = "Zobrazení detailu série", contentType = "Série", contentId = result.name)

    }

}
