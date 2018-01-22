package cz.kutner.comicsdb.classifiedList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.Classified
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class ClassifiedListViewModel(application: Application) :
    AbstractAndroidViewModel<Classified>(application) {
    override fun getJob(): Deferred<List<Classified>?> = async(CommonPool) {
        retrofitModule.classifiedListService.filteredClassifiedList(start * count, count, filterId)
            .execute().body()
    }
}