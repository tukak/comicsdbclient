package cz.kutner.comicsdb.comicsList

import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Comics
import kotlinx.coroutines.experimental.Deferred

class ComicsListViewModel(retrofitModule: RetrofitModule) :
    AbstractAndroidViewModel<Comics>(retrofitModule) {
    override fun getJob(): Deferred<List<Comics>?> =
        retrofitModule.comicsListService.comicsList(start * count, count, searchText)
}