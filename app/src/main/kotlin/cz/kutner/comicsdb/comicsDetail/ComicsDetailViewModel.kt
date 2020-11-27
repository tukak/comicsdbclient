package cz.kutner.comicsdb.comicsDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.network.RetrofitModule

class ComicsDetailViewModel(retrofitModule: RetrofitModule) :
    AbstractViewModel<ComicsDetail>(retrofitModule) {
    override suspend fun getJob(id: Int): ComicsDetail =
        retrofitModule.comicsDetailService.getComics(id)
}