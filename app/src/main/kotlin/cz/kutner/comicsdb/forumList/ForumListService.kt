package cz.kutner.comicsdb.forumList

import cz.kutner.comicsdb.model.ForumEntry
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForumListService {
    @GET("/api.php?get=forum")
    fun filteredForumList(@Query("start") start: Int, @Query("records") records: Int, @Query("id") forumId: Int): Call<List<ForumEntry>>
}
