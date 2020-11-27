package cz.kutner.comicsdb.authorList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.network.RetrofitModule

class AuthorListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<Author>(retrofitModule) {
    override suspend fun getJob(): List<Author> =
        retrofitModule.authorListService.listAuthors(start * count, count, searchText)
}