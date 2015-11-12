package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Comics
import retrofit.http.GET
import retrofit.http.Query

interface ComicsService {
    @GET("/comics.php")
    fun getComics(@Query("id") comicsId: Int): Comics
}
