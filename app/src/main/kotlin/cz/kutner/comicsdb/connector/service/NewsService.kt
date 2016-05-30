package cz.kutner.comicsdb.connector.service

import cz.kutner.comicsdb.model.NewsItem
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("/index.php")
    fun listNews(): Call<List<NewsItem>>
}
