package cz.kutner.comicsdb.service;

import java.util.List;

import cz.kutner.comicsdb.model.Series;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface SeriesService {
    @GET("/serielist.php")
    Observable<List<Series>> seriesList(@Query("str") int page);

    @GET("/search.php")
    Observable<List<Series>> seriesSearch(@Query("searchfor") String keyword);
}
