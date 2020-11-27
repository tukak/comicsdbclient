package cz.kutner.comicsdb.classifiedList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.network.RetrofitModule

class ClassifiedListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<Classified>(retrofitModule) {
    override suspend fun getJob(): List<Classified> =
        retrofitModule.classifiedListService.filteredClassifiedList(start * count, count, filterId)
}