package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.AuthorDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthorDetailService {
    @GET("/autor.php")
    fun authorDetail(@Query("id") id: Int): Call<AuthorDetail>
}
