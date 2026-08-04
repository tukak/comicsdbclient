package cz.kutner.comicsdb.authorDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.model.AuthorDetail

class AuthorDetailViewModel(private val service: AuthorDetailService) :
    AbstractViewModel<AuthorDetail>() {
    override suspend fun getJob(id: Int): AuthorDetail =
        service.authorDetail(id)
}
