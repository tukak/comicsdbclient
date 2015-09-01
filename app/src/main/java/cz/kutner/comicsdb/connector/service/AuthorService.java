package cz.kutner.comicsdb.connector.service;

import java.util.List;

import cz.kutner.comicsdb.model.Author;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface AuthorService {
    @GET("/autorlist.php")
    Observable<List<Author>> listAuthors(@Query("str") int page);

    @GET("/search.php")
    Observable<List<Author>> authorSearch(@Query("searchfor") String keyword);
}
