package cz.kutner.comicsdb.activity

import cz.kutner.comicsdb.adapter.ComicsDetailAdapter
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.utils.fromHtml
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*

class ComicsDetailActivity : AbstractDetailActivity<ComicsDetail>() {

    override fun loadData() {
        runAsync(retrofitModule.comicsDetailService.getComics(id))
    }

    override fun showData() {
        supportActionBar?.title = result.name.fromHtml()
        val adapter = ComicsDetailAdapter(layoutInflater, listOf(result) + result.comments)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(contentName = "Zobrazení detailu komiksu", contentType = "Comics", contentId = result.name)
    }
}
