package cz.kutner.comicsdb.classifiedList

import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.Classified
import kotlinx.coroutines.experimental.Deferred

class ClassifiedListViewModel(retrofitModule: RetrofitModule) :
    AbstractAndroidViewModel<Classified>(retrofitModule) {
    override fun getJob(): Deferred<List<Classified>?> =
        retrofitModule.classifiedListService.filteredClassifiedList(start * count, count, filterId)
}