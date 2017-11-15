package cz.kutner.comicsdb.newsList

import cz.kutner.comicsdb.model.NewsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsListService {
    @GET("/api.php?get=news")
    fun listNews(@Query("start") start: Int, @Query("records") records: Int): Call<List<NewsItem>>
}
