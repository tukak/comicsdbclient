package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Series
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesDetailService {
    @GET("/serie.php")
    fun seriesDetail(@Query("id") id: Int): Call<Series>
}
