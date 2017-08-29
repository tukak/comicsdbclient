package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.ComicsDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsDetailService {
    @GET("/api.php?get=comics_detail")
    fun getComics(@Query("id") comicsId: Int): Call<ComicsDetail>
}
