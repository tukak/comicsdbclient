package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Author
import retrofit.http.GET
import retrofit.http.Query

interface AuthorService {
    @GET("/autorlist.php")
    fun listAuthors(@Query("str") page: Int): List<Author>

    @GET("/search.php")
    fun authorSearch(@Query("searchfor") keyword: String): List<Author>
}
