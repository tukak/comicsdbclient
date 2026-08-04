package cz.kutner.comicsdb.comicsList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.Comics

class ComicsListViewModel(private val service: ComicsListService) :
    AbstractPagedViewModel<Comics>() {
    override suspend fun getJob(): List<Comics> =
        service.comicsList(start * count, count, searchText)
}
