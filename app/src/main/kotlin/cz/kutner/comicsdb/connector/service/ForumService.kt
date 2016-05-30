package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.ForumEntry
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForumService {
    @GET("/forum.php")
    fun filteredForumList(@Query("str") page: Int, @Query("id") forumId: Int, @Query("val") searchText: String): Call<List<ForumEntry>>
}
