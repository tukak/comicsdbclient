package cz.kutner.comicsdb.seriesDetail

import androidx.lifecycle.ViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.SeriesDetail
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.runBlocking

class SeriesDetailViewModel(val retrofitModule: RetrofitModule) : ViewModel() {

    private var seriesDetail: SeriesDetail? = null

    fun getSeriesDetail(id: Int): SeriesDetail = seriesDetail ?: runBlocking(CommonPool) {
        retrofitModule.seriesDetailService.seriesDetail(id).await()
    }

}