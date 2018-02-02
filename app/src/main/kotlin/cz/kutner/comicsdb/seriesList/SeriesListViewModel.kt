package cz.kutner.comicsdb.seriesList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.Series
import kotlinx.coroutines.experimental.Deferred

class SeriesListViewModel(application: Application) :
    AbstractAndroidViewModel<Series>(application) {
    override fun getJob(): Deferred<List<Series>?> =
        retrofitModule.seriesListService.getSeriesList(start * count, count, searchText)
}