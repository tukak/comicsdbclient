package cz.kutner.comicsdb.seriesDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.model.SeriesDetail
import cz.kutner.comicsdb.network.RetrofitModule
import kotlinx.coroutines.Deferred

class SeriesDetailViewModel(retrofitModule: RetrofitModule) :
    AbstractViewModel<SeriesDetail>(retrofitModule) {
    override suspend fun getJob(id: Int): SeriesDetail =
        retrofitModule.seriesDetailService.seriesDetail(id)
}