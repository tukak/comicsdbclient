package cz.kutner.comicsdb.authorList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.Author
import kotlinx.coroutines.experimental.Deferred

class AuthorListViewModel(application: Application) :
    AbstractAndroidViewModel<Author>(application) {
    override fun getJob(): Deferred<List<Author>?> =
        retrofitModule.authorListService.listAuthors(start * count, count, searchText)
}