package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Comics
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsListService {
    @GET("/comicslist.php")
    fun comicsList(@Query("str") page: Int): Call<List<Comics>>

    @GET("/search.php")
    fun comicsSearch(@Query("searchfor") keyword: String): Call<List<Comics>>

}
