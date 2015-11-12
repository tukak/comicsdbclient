package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Comics
import retrofit.http.GET
import retrofit.http.Query

interface ComicsListService {
    @GET("/comicslist.php")
    fun comicsList(@Query("str") page: Int): List<Comics>

    @GET("/search.php")
    fun comicsSearch(@Query("searchfor") keyword: String): List<Comics>

}
