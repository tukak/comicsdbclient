package cz.kutner.comicsdb.comicsList

import cz.kutner.comicsdb.model.Comics
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsListService {
    @GET("/api.php?get=comics_list")
    suspend fun comicsList(@Query("start") start: Int, @Query("records") records: Int, @Query("search") keyword: String): List<Comics>
}
