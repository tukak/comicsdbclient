package cz.kutner.comicsdb.seriesDetail

import androidx.core.text.parseAsHtml
import cz.kutner.comicsdb.abstracts.AbstractDetailActivity
import cz.kutner.comicsdb.model.SeriesDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesDetailActivity : AbstractDetailActivity<SeriesDetail>() {
    override val model: SeriesDetailViewModel by viewModel()

    override fun processResult(result: SeriesDetail) {
        supportActionBar?.title = result.name.parseAsHtml()
        val adapter = SeriesDetailAdapter(layoutInflater, listOf(result) + result.comicses)
        binding.fragmentListInclude.recyclerView.adapter = adapter
        binding.fragmentListInclude.recyclerView.setHasFixedSize(true)
    }
}
