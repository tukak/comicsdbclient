package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Comics
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

interface ComicsService {
    @GET("/comics.php")
    fun getComics(@Query("id") comicsId: Int): Observable<Comics>
}
