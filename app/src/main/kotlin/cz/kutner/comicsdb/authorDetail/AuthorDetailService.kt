package cz.kutner.comicsdb.authorDetail

import cz.kutner.comicsdb.model.AuthorDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthorDetailService {
    @GET("/api.php?get=autor_detail&start=0&records=2147483647")
    fun authorDetail(@Query("id") id: Int): Call<AuthorDetail>
}
