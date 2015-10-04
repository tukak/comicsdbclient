package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.NewsItem
import retrofit.http.GET
import rx.Observable

interface NewsService {
    @GET("/index.php")
    fun listNews(): Observable<List<NewsItem>>
}
