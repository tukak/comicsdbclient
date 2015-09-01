package cz.kutner.comicsdb.connector.service;

import java.util.List;

import cz.kutner.comicsdb.model.NewsItem;
import retrofit.http.GET;
import rx.Observable;

public interface NewsService {
    @GET("/index.php")
    Observable<List<NewsItem>> listNews();
}
