package cz.kutner.comicsdb.seriesDetail

import cz.kutner.comicsdb.model.SeriesDetail
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesDetailService {
    @GET("/api.php?get=serie_detail&start=0&records=2147483647")
    fun seriesDetail(@Query("id") id: Int): Deferred<SeriesDetail>
}
