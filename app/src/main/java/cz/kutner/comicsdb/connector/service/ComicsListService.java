package cz.kutner.comicsdb.connector.service;

import java.util.List;

import cz.kutner.comicsdb.model.Comics;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ComicsListService {
    @GET("/comicslist.php")
    Observable<List<Comics>> comicsList(@Query("str") int page);

    @GET("/search.php")
    Observable<List<Comics>> comicsSearch(@Query("searchfor") String keyword);

}
