package cz.kutner.comicsdb.authorDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.model.AuthorDetail
import cz.kutner.comicsdb.network.RetrofitModule
import kotlinx.coroutines.experimental.Deferred

class AuthorDetailViewModel(retrofitModule: RetrofitModule) :
    AbstractViewModel<AuthorDetail>(retrofitModule) {
    override fun getJob(id: Int): Deferred<AuthorDetail> =
        retrofitModule.authorDetailService.authorDetail(id)
}