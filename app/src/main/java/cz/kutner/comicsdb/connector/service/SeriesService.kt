package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Series
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

interface SeriesService {
    @GET("/serielist.php")
    fun getSeriesList(@Query("str") page: Int): Observable<List<Series>>

    @GET("/search.php")
    fun searchSeries(@Query("searchfor") keyword: String): Observable<List<Series>>
}
