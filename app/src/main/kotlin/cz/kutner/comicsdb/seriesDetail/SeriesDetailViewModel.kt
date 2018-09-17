package cz.kutner.comicsdb.seriesDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.model.SeriesDetail
import cz.kutner.comicsdb.network.RetrofitModule
import kotlinx.coroutines.Deferred

class SeriesDetailViewModel(retrofitModule: RetrofitModule) :
    AbstractViewModel<SeriesDetail>(retrofitModule) {
    override fun getJob(id: Int): Deferred<SeriesDetail> =
        retrofitModule.seriesDetailService.seriesDetail(id)
}