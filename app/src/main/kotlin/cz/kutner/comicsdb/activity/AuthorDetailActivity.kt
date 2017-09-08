package cz.kutner.comicsdb.activity

import cz.kutner.comicsdb.adapter.AuthorDetailAdapter
import cz.kutner.comicsdb.model.AuthorDetail
import cz.kutner.comicsdb.utils.fromHtml
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*

class AuthorDetailActivity : AbstractDetailActivity<AuthorDetail>() {

    override fun loadData() {
        runAsync(retrofitModule.authorDetailService.authorDetail(id))
    }

    override fun showData() {
        supportActionBar?.title = result.name.fromHtml()
        val adapter = AuthorDetailAdapter(this, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(contentName = "Zobrazení detailu autora", contentType = "Autor", contentId = result.name)
    }
}
