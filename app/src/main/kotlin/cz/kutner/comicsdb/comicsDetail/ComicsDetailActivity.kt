package cz.kutner.comicsdb.comicsDetail

import androidx.core.text.parseAsHtml
import cz.kutner.comicsdb.abstracts.AbstractDetailActivity
import cz.kutner.comicsdb.model.ComicsDetail
import org.koin.androidx.viewmodel.ext.android.viewModel


class ComicsDetailActivity : AbstractDetailActivity<ComicsDetail>() {
    override val model: ComicsDetailViewModel by viewModel()

    override fun processResult(result: ComicsDetail) {
        supportActionBar?.title = result.name.parseAsHtml()
        val adapter =
            ComicsDetailAdapter(layoutInflater, listOf(result) + result.comments)
        binding.fragmentListInclude.recyclerView.adapter = adapter
        binding.fragmentListInclude.recyclerView.setHasFixedSize(true)
    }
}
