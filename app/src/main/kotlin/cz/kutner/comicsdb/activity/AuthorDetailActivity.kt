package cz.kutner.comicsdb.activity

import android.os.Bundle
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.AuthorDetailAdapter
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.utils.Utils
import kotlinx.android.synthetic.main.fragment_list.*

class AuthorDetailActivity : AbstractDetailActivity<Author>() {

    override val prefix: String by lazy { getString(R.string.url_author_detail) }
    override val extraName = MainActivity.AUTHOR_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun loadData() {
        runAsync(retrofitModule.authorDetailService.authorDetail(id))
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
