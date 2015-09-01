package cz.kutner.comicsdb.connector.service;

import java.util.List;

import cz.kutner.comicsdb.model.ForumEntry;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ForumService {
    @GET("/forum.php")
    Observable<List<ForumEntry>> filteredForumList(@Query("str") int page, @Query("id") int forumId, @Query("val") String searchText);
}
