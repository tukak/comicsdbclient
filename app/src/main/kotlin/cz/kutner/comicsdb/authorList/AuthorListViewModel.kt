package cz.kutner.comicsdb.authorList

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Author
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.koin.android.ext.android.getKoin

class AuthorListViewModel(application: Application) : AndroidViewModel(application) {
    val retrofitModule by lazy { getApplication<Application>().getKoin().get<RetrofitModule>() }
    var start = 0
    var count = 20
    var searchText = ""

    private val authors = MutableLiveData<List<Author>>()

    fun getAuthors(): LiveData<List<Author>> {
        if (authors.value == null) {
            loadAuthors()
        }
        return authors
    }

    fun loadAuthors() = async(UI) {
        val job = async(CommonPool) {
            retrofitModule.authorListService.listAuthors(start * count, count, searchText).execute().body()
        }
        val newAuthors = job.await()
        start++
        if (newAuthors != null) {
            if (authors.value == null) {
                authors.value = newAuthors
            } else {
                authors.value = authors.value?.plus(newAuthors)
            }
        }
    }
}