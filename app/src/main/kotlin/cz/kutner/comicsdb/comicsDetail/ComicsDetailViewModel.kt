package cz.kutner.comicsdb.comicsDetail

import android.arch.lifecycle.ViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.ComicsDetail
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking

class ComicsDetailViewModel(val retrofitModule: RetrofitModule) : ViewModel() {

    private var comicsDetail: ComicsDetail? = null

    fun getComicsDetail(id: Int): ComicsDetail =
        comicsDetail ?: runBlocking(CommonPool) {
            retrofitModule.comicsDetailService.getComics(id).await()
        }

}