package cz.kutner.comicsdb.comicsList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.Comics
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class ComicsListViewModel(application: Application) : AbstractAndroidViewModel<Comics>(application) {
    override fun loadData(): Deferred<Unit> {
        job = async(CommonPool) {
            retrofitModule.comicsListService.comicsList(start * count, count, searchText).execute().body()
        }
        return super.loadData()
    }
}