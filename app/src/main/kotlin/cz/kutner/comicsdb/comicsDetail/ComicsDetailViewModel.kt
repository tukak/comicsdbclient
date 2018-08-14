package cz.kutner.comicsdb.comicsDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.ComicsDetail
import kotlinx.coroutines.experimental.Deferred

class ComicsDetailViewModel(retrofitModule: RetrofitModule) :
    AbstractViewModel<ComicsDetail>(retrofitModule) {
    override fun getJob(id: Int): Deferred<ComicsDetail> =
        retrofitModule.comicsDetailService.getComics(id)
}