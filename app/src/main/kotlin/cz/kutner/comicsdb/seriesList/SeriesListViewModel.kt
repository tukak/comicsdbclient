package cz.kutner.comicsdb.seriesList

import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Series
import kotlinx.coroutines.experimental.Deferred

class SeriesListViewModel(retrofitModule: RetrofitModule) :
    AbstractAndroidViewModel<Series>(retrofitModule) {
    override fun getJob(): Deferred<List<Series>?> =
        retrofitModule.seriesListService.getSeriesList(start * count, count, searchText)
}