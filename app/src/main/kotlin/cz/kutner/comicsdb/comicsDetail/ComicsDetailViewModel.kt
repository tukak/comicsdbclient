package cz.kutner.comicsdb.comicsDetail

import cz.kutner.comicsdb.abstracts.AbstractViewModel
import cz.kutner.comicsdb.model.ComicsDetail

class ComicsDetailViewModel(private val service: ComicsDetailService) :
    AbstractViewModel<ComicsDetail>() {
    override suspend fun getJob(id: Int): ComicsDetail =
        service.getComics(id)
}
