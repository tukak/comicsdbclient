package cz.kutner.comicsdb.authorList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.Author

class AuthorListViewModel(private val service: AuthorListService) :
    AbstractPagedViewModel<Author>() {
    override suspend fun getJob(): List<Author> =
        service.listAuthors(start * count, count, searchText)
}
