package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.NewsItem
import retrofit.http.GET

interface NewsService {
    @GET("/index.php")
    fun listNews(): List<NewsItem>
}
