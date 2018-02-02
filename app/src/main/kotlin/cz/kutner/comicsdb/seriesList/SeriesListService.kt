package cz.kutner.comicsdb.seriesList

import cz.kutner.comicsdb.model.Series
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesListService {
    @GET("/api.php?get=serie")
    fun getSeriesList(@Query("start") start: Int, @Query("records") records: Int, @Query("search") keyword: String): Deferred<List<Series>>
}
