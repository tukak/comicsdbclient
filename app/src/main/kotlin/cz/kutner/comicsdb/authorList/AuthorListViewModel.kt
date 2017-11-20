package cz.kutner.comicsdb.authorList

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Author
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.koin.android.ext.android.getKoin
import timber.log.Timber

class AuthorListViewModel(application: Application) : AndroidViewModel(application) {
    val retrofitModule by lazy { getApplication<Application>().getKoin().get<RetrofitModule>() }
    var start = 0
    var count = 20

    private val authors = mutableListOf<Author>()

    fun getAuthors(searchText: String = ""): List<Author> {
        Timber.i("Getting authors")
        if (authors.isEmpty()) {
            loadAuthors(searchText)
        }
        Timber.i("Got ${authors.size} authors")
        return authors
    }

    fun loadMoreAuthors(searchText: String) {
        Timber.i("Loading more authors")
        start++
        loadAuthors(searchText)
    }

    private fun loadAuthors(searchText: String) {
        Timber.i("Loading authors")
        val newAuthors = runBlocking {
            async(CommonPool) {
                return@async retrofitModule.authorListService.listAuthors(start * count, count, searchText).execute().body()
            }.await()
        }
        if (newAuthors != null) {
            authors.addAll(newAuthors)
        }
    }
}