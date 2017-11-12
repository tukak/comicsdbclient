package cz.kutner.comicsdb.authorDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.AuthorDetail
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.koin.android.ext.android.getKoin

class AuthorDetailViewModel(application: Application) : AndroidViewModel(application) {
    val retrofitModule by lazy { getApplication<Application>().getKoin().get<RetrofitModule>() }

    private var authorDetail: AuthorDetail? = null

    fun getAuthorDetail(id: Int): AuthorDetail {
        if (authorDetail == null) {
            authorDetail = loadAuthorDetail(id)
        }
        return authorDetail!!
    }

    private fun loadAuthorDetail(id: Int): AuthorDetail? {
        return runBlocking {
            async(CommonPool) {
                return@async retrofitModule.authorDetailService.authorDetail(id).execute().body()
            }.await()
        }
    }
}