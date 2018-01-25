package cz.kutner.comicsdb.seriesDetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.SeriesDetail
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SeriesDetailViewModel(application: Application) : AndroidViewModel(application),
    KoinComponent {
    val retrofitModule by inject<RetrofitModule>()

    private var seriesDetail: SeriesDetail? = null

    fun getSeriesDetail(id: Int): SeriesDetail = seriesDetail ?: runBlocking(CommonPool) {
        retrofitModule.seriesDetailService.seriesDetail(id).await()
    }

}