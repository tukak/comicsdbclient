package cz.kutner.comicsdb.newsList

import cz.kutner.comicsdb.model.NewsItem
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsListService {
    @GET("/api.php?get=news")
    suspend fun listNews(@Query("start") start: Int, @Query("records") records: Int): List<NewsItem>
}
