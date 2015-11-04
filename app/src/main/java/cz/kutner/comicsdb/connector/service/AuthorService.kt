package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Author
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

interface AuthorService {
    @GET("/autorlist.php")
    fun listAuthors(@Query("str") page: Int): Observable<List<Author>>

    @GET("/search.php")
    fun authorSearch(@Query("searchfor") keyword: String): Observable<List<Author>>

    @GET("/author.php")
    fun authorDetail(@Query("id") id: Int): Observable<Author>
}
