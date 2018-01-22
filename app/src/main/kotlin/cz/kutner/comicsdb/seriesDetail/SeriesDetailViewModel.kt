package cz.kutner.comicsdb.seriesDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.SeriesDetail
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SeriesDetailViewModel(application: Application) : AndroidViewModel(application),
    KoinComponent {
    val retrofitModule by inject<RetrofitModule>()

    private var seriesDetail: SeriesDetail? = null

    fun getSeriesDetail(id: Int): SeriesDetail {
        if (seriesDetail == null) {
            seriesDetail = loadSeriesDetail(id)
        }
        return seriesDetail!!
    }

    private fun loadSeriesDetail(id: Int): SeriesDetail? {
        return runBlocking {
            async(CommonPool) {
                return@async retrofitModule.seriesDetailService.seriesDetail(id).execute().body()
            }.await()
        }
    }
}