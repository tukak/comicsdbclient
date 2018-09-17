package cz.kutner.comicsdb.comicsList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.Comics
import kotlinx.coroutines.Deferred

class ComicsListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<Comics>(retrofitModule) {
    override fun getJob(): Deferred<List<Comics>?> =
        retrofitModule.comicsListService.comicsList(start * count, count, searchText)
}