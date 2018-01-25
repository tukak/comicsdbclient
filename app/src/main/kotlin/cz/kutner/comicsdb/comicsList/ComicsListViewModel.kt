package cz.kutner.comicsdb.comicsList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.Comics
import kotlinx.coroutines.experimental.Deferred

class ComicsListViewModel(application: Application) :
    AbstractAndroidViewModel<Comics>(application) {
    override fun getJob(): Deferred<List<Comics>?> =
        retrofitModule.comicsListService.comicsList(start * count, count, searchText)
}