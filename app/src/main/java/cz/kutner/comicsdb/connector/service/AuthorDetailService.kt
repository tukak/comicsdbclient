package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Author
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

interface AuthorDetailService {
    @GET("/autor.php")
    fun authorDetail(@Query("id") id: Int): Observable<Author>
}
