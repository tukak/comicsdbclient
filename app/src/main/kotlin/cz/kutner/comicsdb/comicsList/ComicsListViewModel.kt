package cz.kutner.comicsdb.comicsList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.network.RetrofitModule

class ComicsListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<Comics>(retrofitModule) {
    override suspend fun getJob(): List<Comics> =
        retrofitModule.comicsListService.comicsList(start * count, count, searchText)
}