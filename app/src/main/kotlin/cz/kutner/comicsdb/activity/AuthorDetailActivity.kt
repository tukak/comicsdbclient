package cz.kutner.comicsdb.activity

import android.os.Bundle
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.AuthorDetailAdapter
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.utils.Utils
import kotlinx.android.synthetic.main.fragment_list.*
import space.traversal.kapsule.Injects

class AuthorDetailActivity : AbstractDetailActivity<Author>(), Injects<RetrofitModule> {

    override val prefix: String by lazy { getString(R.string.url_author_detail) }
    override val extraName = MainActivity.AUTHOR_ID

    private val authorDetailService by required { authorDetailService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject((application as ComicsDBApplication).retfofitModule)
    }

    override fun loadData() {
        runAsync(authorDetailService.authorDetail(id))
    }

    override fun showData() {
        supportActionBar?.title = result.name
        val adapter = AuthorDetailAdapter(this, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        Utils.logVisit(contentName = "Zobrazení detailu autora", contentType = "Autor", contentId = result.name)
    }
}
