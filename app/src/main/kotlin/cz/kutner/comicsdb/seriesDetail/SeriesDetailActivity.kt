package cz.kutner.comicsdb.seriesDetail

import cz.kutner.comicsdb.abstracts.AbstractDetailActivity
import cz.kutner.comicsdb.model.SeriesDetail
import cz.kutner.comicsdb.utils.fromHtml
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.architecture.ext.viewModel

class SeriesDetailActivity : AbstractDetailActivity<SeriesDetail>() {
    val model: SeriesDetailViewModel by viewModel()
    override fun loadData() {
        result = model.getSeriesDetail(id)
        supportActionBar?.title = result.name.fromHtml()
        val adapter = SeriesDetailAdapter(layoutInflater, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(
            contentName = "Zobrazení detailu série",
            contentType = "Série",
            contentId = result.name
        )

    }

}
