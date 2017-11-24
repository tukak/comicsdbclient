package cz.kutner.comicsdb.seriesList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.Series
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class SeriesListViewModel(application: Application) : AbstractAndroidViewModel<Series>(application) {
    override fun loadData(): Deferred<Unit> {
        job = async(CommonPool) {
            retrofitModule.seriesListService.getSeriesList(start * count, count, searchText).execute().body()
        }
        return super.loadData()
    }
}