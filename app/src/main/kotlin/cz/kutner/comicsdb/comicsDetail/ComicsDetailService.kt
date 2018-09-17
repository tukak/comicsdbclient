package cz.kutner.comicsdb.comicsDetail

import cz.kutner.comicsdb.model.ComicsDetail
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsDetailService {
    @GET("/api.php?get=comics_detail")
    fun getComics(@Query("id") comicsId: Int): Deferred<ComicsDetail>
}
