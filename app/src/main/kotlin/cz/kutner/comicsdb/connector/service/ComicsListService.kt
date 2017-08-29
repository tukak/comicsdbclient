package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Comics
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsListService {
    @GET("/api.php?get=comics_list")
    fun comicsList(@Query("start") start: Int, @Query("records") records: Int, @Query("search") keyword: String): Call<List<Comics>>
}
