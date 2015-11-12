package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Series
import retrofit.http.GET
import retrofit.http.Query

interface SeriesService {
    @GET("/serielist.php")
    fun getSeriesList(@Query("str") page: Int): List<Series>

    @GET("/search.php")
    fun searchSeries(@Query("searchfor") keyword: String): List<Series>
}
