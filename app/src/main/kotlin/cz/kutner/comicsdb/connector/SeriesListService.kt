package cz.kutner.comicsdb.connector

import cz.kutner.comicsdb.model.Series
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesListService {
    @GET("/api.php?get=serie")
    fun getSeriesList(@Query("start") start: Int, @Query("records") records: Int, @Query("search") keyword: String): Call<List<Series>>
}