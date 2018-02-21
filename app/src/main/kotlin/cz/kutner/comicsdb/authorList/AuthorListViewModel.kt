package cz.kutner.comicsdb.authorList

import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Author
import kotlinx.coroutines.experimental.Deferred

class AuthorListViewModel(retrofitModule: RetrofitModule) :
    AbstractAndroidViewModel<Author>(retrofitModule) {
    override fun getJob(): Deferred<List<Author>?> =
        retrofitModule.authorListService.listAuthors(start * count, count, searchText)
}