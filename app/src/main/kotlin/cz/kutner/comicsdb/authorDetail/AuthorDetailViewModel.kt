package cz.kutner.comicsdb.authorDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.AuthorDetail
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class AuthorDetailViewModel(application: Application) : AndroidViewModel(application),
    KoinComponent {
    val retrofitModule by inject<RetrofitModule>()

    private var authorDetail: AuthorDetail? = null

    fun getAuthorDetail(id: Int): AuthorDetail = authorDetail ?: runBlocking(CommonPool) {
        retrofitModule.authorDetailService.authorDetail(id).await()
    }
}