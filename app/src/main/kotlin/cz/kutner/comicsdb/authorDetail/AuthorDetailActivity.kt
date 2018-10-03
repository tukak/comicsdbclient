package cz.kutner.comicsdb.authorDetail

import androidx.core.text.parseAsHtml
import cz.kutner.comicsdb.abstracts.AbstractDetailActivity
import cz.kutner.comicsdb.model.AuthorDetail
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorDetailActivity : AbstractDetailActivity<AuthorDetail>() {
    override val model: AuthorDetailViewModel by viewModel()

    override fun processResult(result: AuthorDetail) {
        supportActionBar?.title = result.name.parseAsHtml()
        val adapter = AuthorDetailAdapter(layoutInflater, listOf(result) + result.comicses)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
    }
}
