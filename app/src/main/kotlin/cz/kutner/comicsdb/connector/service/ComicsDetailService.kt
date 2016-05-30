package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.Comics
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsDetailService {
    @GET("/comics.php")
    fun getComics(@Query("id") comicsId: Int): Call<Comics>
}
