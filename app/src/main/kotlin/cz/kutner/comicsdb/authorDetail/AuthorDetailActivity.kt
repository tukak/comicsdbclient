package cz.kutner.comicsdb.authorDetail

import androidx.core.text.parseAsHtml
import cz.kutner.comicsdb.abstracts.AbstractDetailActivity
import cz.kutner.comicsdb.model.AuthorDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorDetailActivity : AbstractDetailActivity<AuthorDetail>() {
    override val model: AuthorDetailViewModel by viewModel()

    override fun processResult(result: AuthorDetail) {
        supportActionBar?.title = result.name.parseAsHtml()
        val adapter = AuthorDetailAdapter(layoutInflater, listOf(result) + result.comicses)
        binding.fragmentListInclude.recyclerView.adapter = adapter
        binding.fragmentListInclude.recyclerView.setHasFixedSize(true)
    }
}
