package cz.kutner.comicsdb.connector.service;

import java.util.List;

import cz.kutner.comicsdb.model.Classified;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ClassifiedService {
    @GET("/bazar.php")
    Observable<List<Classified>> filteredClassifiedList(@Query("str") int page, @Query("id") int categoryId, @Query("val")  String searchText);
}
