package cz.kutner.comicsdb.authorDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.model.AuthorDetail
import cz.kutner.comicsdb.network.RetrofitModule

class AuthorDetailViewModel(retrofitModule: RetrofitModule) :
    AbstractViewModel<AuthorDetail>(retrofitModule) {
    override suspend fun getJob(id: Int): AuthorDetail =
        retrofitModule.authorDetailService.authorDetail(id)
}