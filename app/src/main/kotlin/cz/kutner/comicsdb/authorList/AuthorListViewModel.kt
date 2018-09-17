package cz.kutner.comicsdb.authorList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.Author
import kotlinx.coroutines.Deferred

class AuthorListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<Author>(retrofitModule) {
    override fun getJob(): Deferred<List<Author>?> =
        retrofitModule.authorListService.listAuthors(start * count, count, searchText)
}