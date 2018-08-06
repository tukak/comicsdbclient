package cz.kutner.comicsdb.comicsDetail

import androidx.core.text.parseAsHtml
import cz.kutner.comicsdb.abstracts.AbstractDetailActivity
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComicsDetailActivity : AbstractDetailActivity<ComicsDetail>() {

    val model: ComicsDetailViewModel by viewModel()

    override fun loadData() {
        val result = model.getComicsDetail(id)
        supportActionBar?.title = result.name.parseAsHtml()
        val adapter = ComicsDetailAdapter(layoutInflater, listOf(result) + result.comments)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        firebase.logVisit(
            contentName = "Zobrazení detailu komiksu",
            contentType = "Comics",
            contentId = result.name
        )
    }

}
