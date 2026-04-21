package cz.kutner.comicsdb.seriesList

import cz.kutner.comicsdb.model.Series
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesListService {
    @GET("/api.php?get=serie")
    suspend fun getSeriesList(@Query("start") start: Int, @Query("records") records: Int, @Query("search") keyword: String): List<Series>
}
