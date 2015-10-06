package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.ForumEntry
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

interface ForumService {
    @GET("/forum.php")
    fun filteredForumList(@Query("str") page: Int, @Query("id") forumId: Int, @Query("val") searchText: String): Observable<List<ForumEntry>>
}
