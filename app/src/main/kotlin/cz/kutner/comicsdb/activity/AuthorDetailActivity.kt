package cz.kutner.comicsdb.activity

import android.os.Bundle
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.AuthorDetailAdapter
import cz.kutner.comicsdb.connector.service.AuthorDetailService
import cz.kutner.comicsdb.di.Tracker
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.utils.Utils
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class AuthorDetailActivity : AbstractDetailActivity() {

    override val prefix: String by lazy { getString(R.string.url_author_detail) }
    override val extraName = MainActivity.AUTHOR_ID

    @Inject lateinit var authorDetailService: AuthorDetailService
    @Inject lateinit var tracker: Tracker

    private lateinit var author: Author

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as ComicsDBApplication).applicationComponent.inject(this)
    }

    override fun loadData() {
        doAsync() {
            author = authorDetailService.authorDetail(id).execute().body()
            uiThread {
                showData()
            }
        }
    }

    override fun showData() {
        supportActionBar?.title = author.name
        val adapter = AuthorDetailAdapter(this, listOf(author) + author.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        tracker.logVisit(screenName = "AuthorDetailFragment", category = "Detail", action = author.name)
        Utils.logVisitToFabricAnswers(contentName = "Zobrazení detailu autora", contentType = "Autor", contentId = author.name)
    }
}
