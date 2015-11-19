package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Author
import retrofit.http.GET
import retrofit.http.Query

interface SeriesDetailService {
    @GET("/serie.php")
    fun seriesDetail(@Query("id") id: Int): Author
}
