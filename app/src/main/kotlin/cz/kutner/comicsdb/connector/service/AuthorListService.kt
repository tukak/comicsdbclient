package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Author
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthorListService {
    @GET("/autorlist.php")
    fun listAuthors(@Query("str") page: Int): Call<List<Author>>

    @GET("/search.php")
    fun authorSearch(@Query("searchfor") keyword: String): Call<List<Author>>
}
