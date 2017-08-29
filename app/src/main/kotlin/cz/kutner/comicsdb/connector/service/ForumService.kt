package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.ForumEntry
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForumService {
    @GET("/api.php?get=forum")
    fun filteredForumList(@Query("start") start: Int, @Query("records") records: Int): Call<List<ForumEntry>>
}
