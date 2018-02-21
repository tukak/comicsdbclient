package cz.kutner.comicsdb.authorDetail

import android.arch.lifecycle.ViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.AuthorDetail
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking

class AuthorDetailViewModel(val retrofitModule: RetrofitModule) : ViewModel() {

    private var authorDetail: AuthorDetail? = null

    fun getAuthorDetail(id: Int): AuthorDetail = authorDetail ?: runBlocking(CommonPool) {
        retrofitModule.authorDetailService.authorDetail(id).await()
    }
}