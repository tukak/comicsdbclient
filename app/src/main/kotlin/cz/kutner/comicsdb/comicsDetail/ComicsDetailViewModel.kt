package cz.kutner.comicsdb.comicsDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.ComicsDetail
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ComicsDetailViewModel(application: Application) : AndroidViewModel(application),
    KoinComponent {
    val retrofitModule by inject<RetrofitModule>()

    private var comicsDetail: ComicsDetail? = null

    fun getComicsDetail(id: Int): ComicsDetail {
        if (comicsDetail == null) {
            comicsDetail = loadcomicsDetail(id)
        }
        return comicsDetail!!
    }

    private fun loadcomicsDetail(id: Int): ComicsDetail? =
        runBlocking { retrofitModule.comicsDetailService.getComics(id).await() }
        /*return runBlocking {
            async(CommonPool) {
                return@async retrofitModule.comicsDetailService.getComics(id).execute().body()
            }.await()
        }
    }*/
}