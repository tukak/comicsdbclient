package cz.kutner.comicsdb.classifiedList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.Classified

class ClassifiedListViewModel(private val service: ClassifiedListService) :
    AbstractPagedViewModel<Classified>() {
    override suspend fun getJob(): List<Classified> =
        service.filteredClassifiedList(start * count, count, filterId)
}
