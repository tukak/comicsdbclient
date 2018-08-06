package cz.kutner.comicsdb.authorDetail

import androidx.lifecycle.ViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.AuthorDetail
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking

class AuthorDetailViewModel(val retrofitModule: RetrofitModule) : ViewModel() {

    private var authorDetail: AuthorDetail? = null

    fun getAuthorDetail(id: Int): AuthorDetail = authorDetail ?: runBlocking(CommonPool) {
        retrofitModule.authorDetailService.authorDetail(id).await()
    }
}