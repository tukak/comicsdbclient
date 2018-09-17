package cz.kutner.comicsdb.seriesList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.Series
import kotlinx.coroutines.Deferred

class SeriesListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<Series>(retrofitModule) {
    override fun getJob(): Deferred<List<Series>?> =
        retrofitModule.seriesListService.getSeriesList(start * count, count, searchText)
}