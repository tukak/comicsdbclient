package cz.kutner.comicsdb.activity

import android.os.Bundle
import com.google.firebase.perf.metrics.AddTrace
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.AuthorDetailAdapter
import cz.kutner.comicsdb.model.AuthorDetail
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*

class AuthorDetailActivity : AbstractDetailActivity<AuthorDetail>() {

    override val prefix: String by lazy { getString(R.string.url_author_detail) }
    override val extraName = MainActivity.AUTHOR_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @AddTrace(name = "AuthorDetailLoadData")
    override fun loadData() {
        runAsync(retrofitModule.authorDetailService.authorDetail(id))
    }

    override fun showData() {
        supportActionBar?.title = result.name
        val adapter = AuthorDetailAdapter(this, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(contentName = "Zobrazení detailu autora", contentType = "Autor", contentId = result.name)
    }
}
