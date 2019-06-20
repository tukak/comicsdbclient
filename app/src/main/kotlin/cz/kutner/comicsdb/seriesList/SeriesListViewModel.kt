package cz.kutner.comicsdb.seriesList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.Series
import kotlinx.coroutines.Deferred

class SeriesListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<Series>(retrofitModule) {
    override suspend fun getJob(): List<Series>? =
        retrofitModule.seriesListService.getSeriesList(start * count, count, searchText)
}