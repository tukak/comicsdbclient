package cz.kutner.comicsdb.comicsDetail

import android.arch.lifecycle.ViewModelProviders
import cz.kutner.comicsdb.activity.AbstractDetailActivity
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.utils.fromHtml
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*

class ComicsDetailActivity : AbstractDetailActivity<ComicsDetail>() {

    val model by lazy { ViewModelProviders.of(this).get(ComicsDetailViewModel::class.java) }

    override fun loadData() {
        val result = model.getComicsDetail(id)
        supportActionBar?.title = result.name.fromHtml()
        val adapter = ComicsDetailAdapter(layoutInflater, listOf(result) + result.comments)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(contentName = "Zobrazení detailu komiksu", contentType = "Comics", contentId = result.name)
    }

}
