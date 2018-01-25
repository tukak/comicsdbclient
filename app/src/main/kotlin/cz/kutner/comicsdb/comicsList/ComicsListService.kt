package cz.kutner.comicsdb.comicsList

import cz.kutner.comicsdb.model.Comics
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsListService {
    @GET("/api.php?get=comics_list")
    fun comicsList(@Query("start") start: Int, @Query("records") records: Int, @Query("search") keyword: String): Deferred<List<Comics>>
}
