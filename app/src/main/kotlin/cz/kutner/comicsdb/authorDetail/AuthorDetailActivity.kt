package cz.kutner.comicsdb.authorDetail

import cz.kutner.comicsdb.abstracts.AbstractDetailActivity
import cz.kutner.comicsdb.model.AuthorDetail
import cz.kutner.comicsdb.utils.fromHtml
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.architecture.ext.viewModel

class AuthorDetailActivity : AbstractDetailActivity<AuthorDetail>() {

    val model: AuthorDetailViewModel by viewModel()

    override fun loadData() {
        val result = model.getAuthorDetail(id)
        supportActionBar?.title = result.name.fromHtml()
        val adapter = AuthorDetailAdapter(layoutInflater, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(
            contentName = "Zobrazení detailu autora",
            contentType = "Autor",
            contentId = result.name
        )

    }
}
