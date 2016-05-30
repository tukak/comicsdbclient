package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Series
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesListService {
    @GET("/serielist.php")
    fun getSeriesList(@Query("str") page: Int): Call<List<Series>>

    @GET("/search.php")
    fun searchSeries(@Query("searchfor") keyword: String): Call<List<Series>>
}
