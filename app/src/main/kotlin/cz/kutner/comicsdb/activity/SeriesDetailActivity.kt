package cz.kutner.comicsdb.activity

import cz.kutner.comicsdb.adapter.SeriesDetailAdapter
import cz.kutner.comicsdb.model.SeriesDetail
import cz.kutner.comicsdb.utils.fromHtml
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*

class SeriesDetailActivity : AbstractDetailActivity<SeriesDetail>() {

    override fun loadData() {
        runAsync(retrofitModule.seriesDetailService.seriesDetail(id))
    }

    override fun showData() {
        supportActionBar?.title = result.name.fromHtml()
        val adapter = SeriesDetailAdapter(layoutInflater, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(contentName = "Zobrazení detailu série", contentType = "Série", contentId = result.name)
    }

}
