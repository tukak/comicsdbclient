package cz.kutner.comicsdb.connector.service;

import cz.kutner.comicsdb.model.Comics;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ComicsService {
    @GET("/comics.php")
    Observable<Comics> getComics(@Query("id") int comicsId);
}
