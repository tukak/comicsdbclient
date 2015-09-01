package cz.kutner.comicsdb.connector.service;

import java.util.List;

import cz.kutner.comicsdb.model.Series;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface SeriesService {
    @GET("/serielist.php")
    Observable<List<Series>> getSeriesList(@Query("str") int page);

    @GET("/search.php")
    Observable<List<Series>> searchSeries(@Query("searchfor") String keyword);
}
