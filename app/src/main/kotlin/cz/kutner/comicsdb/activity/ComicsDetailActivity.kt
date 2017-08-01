package cz.kutner.comicsdb.activity

import android.os.Bundle
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.ComicsDetailAdapter
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*

class ComicsDetailActivity : AbstractDetailActivity<ComicsDetail>() {

    override val prefix: String by lazy { getString(R.string.url_comics_detail) }
    override val extraName = MainActivity.COMICS_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun loadData() {
        runAsync(retrofitModule.comicsDetailService.getComics(id))
    }

    override fun showData() {
        supportActionBar?.title = result.name
        val adapter = ComicsDetailAdapter(this, listOf(result) + result.comments)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(contentName = "Zobrazení detailu komiksu", contentType = "Comics", contentId = result.name)
    }
}
