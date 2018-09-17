package cz.kutner.comicsdb.forumList

import cz.kutner.comicsdb.model.ForumEntry
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ForumListService {
    @GET("/api.php?get=forum")
    fun filteredForumList(@Query("start") start: Int, @Query("records") records: Int, @Query("id") forumId: Int): Deferred<List<ForumEntry>>
}
