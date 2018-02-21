package cz.kutner.comicsdb.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import cz.kutner.comicsdb.authorDetail.AuthorDetailService
import cz.kutner.comicsdb.authorList.AuthorListService
import cz.kutner.comicsdb.classifiedList.ClassifiedListService
import cz.kutner.comicsdb.comicsDetail.ComicsDetailService
import cz.kutner.comicsdb.comicsList.ComicsListService
import cz.kutner.comicsdb.forumList.ForumListService
import cz.kutner.comicsdb.newsList.NewsListService
import cz.kutner.comicsdb.seriesDetail.SeriesDetailService
import cz.kutner.comicsdb.seriesList.SeriesListService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitModule(okHttpClient: OkHttpClient, baseUrl: String) {
    private val gson = GsonBuilder()
        .setLenient()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
    val seriesListService: SeriesListService by lazy { retrofit.create(
        SeriesListService::class.java) }
    val seriesDetailService: SeriesDetailService by lazy { retrofit.create(
        SeriesDetailService::class.java) }
    val authorDetailService: AuthorDetailService by lazy { retrofit.create(
        AuthorDetailService::class.java) }
    val authorListService: AuthorListService by lazy { retrofit.create(
        AuthorListService::class.java) }
    val classifiedListService: ClassifiedListService by lazy { retrofit.create(
        ClassifiedListService::class.java) }
    val comicsDetailService: ComicsDetailService by lazy { retrofit.create(
        ComicsDetailService::class.java) }
    val comicsListService: ComicsListService by lazy { retrofit.create(
        ComicsListService::class.java) }
    val forumListService: ForumListService by lazy { retrofit.create(ForumListService::class.java) }
    val newsListService: NewsListService by lazy { retrofit.create(NewsListService::class.java) }
}