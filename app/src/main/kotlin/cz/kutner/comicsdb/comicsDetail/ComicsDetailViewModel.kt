package cz.kutner.comicsdb.comicsDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.ComicsDetail
import kotlinx.coroutines.Deferred

class ComicsDetailViewModel(retrofitModule: RetrofitModule) :
    AbstractViewModel<ComicsDetail>(retrofitModule) {
    override suspend fun getJob(id: Int): ComicsDetail =
        retrofitModule.comicsDetailService.getComics(id)
}