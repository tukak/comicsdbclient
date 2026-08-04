package cz.kutner.comicsdb.di

import android.content.Context
import cz.kutner.comicsdb.authorDetail.AuthorDetailService
import cz.kutner.comicsdb.authorDetail.AuthorDetailViewModel
import cz.kutner.comicsdb.authorList.AuthorListService
import cz.kutner.comicsdb.authorList.AuthorListViewModel
import cz.kutner.comicsdb.classifiedList.ClassifiedListService
import cz.kutner.comicsdb.classifiedList.ClassifiedListViewModel
import cz.kutner.comicsdb.comicsDetail.ComicsDetailService
import cz.kutner.comicsdb.comicsDetail.ComicsDetailViewModel
import cz.kutner.comicsdb.comicsList.ComicsListService
import cz.kutner.comicsdb.comicsList.ComicsListViewModel
import cz.kutner.comicsdb.forumList.ForumListService
import cz.kutner.comicsdb.forumList.ForumListViewModel
import cz.kutner.comicsdb.newsList.NewsListService
import cz.kutner.comicsdb.newsList.NewsListViewModel
import cz.kutner.comicsdb.seriesDetail.SeriesDetailService
import cz.kutner.comicsdb.seriesDetail.SeriesDetailViewModel
import cz.kutner.comicsdb.seriesList.SeriesListService
import cz.kutner.comicsdb.seriesList.SeriesListViewModel
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val koinModule = module {
    single { createOkHttpClient(androidContext()) }
    single { createRetrofit(get(), getProperty(SERVER_URL)) }
    single { get<Retrofit>().create<AuthorDetailService>() }
    single { get<Retrofit>().create<AuthorListService>() }
    single { get<Retrofit>().create<ClassifiedListService>() }
    single { get<Retrofit>().create<ComicsDetailService>() }
    single { get<Retrofit>().create<ComicsListService>() }
    single { get<Retrofit>().create<ForumListService>() }
    single { get<Retrofit>().create<NewsListService>() }
    single { get<Retrofit>().create<SeriesListService>() }
    single { get<Retrofit>().create<SeriesDetailService>() }
    viewModel { AuthorDetailViewModel(get()) }
    viewModel { AuthorListViewModel(get()) }
    viewModel { ClassifiedListViewModel(get()) }
    viewModel { ComicsDetailViewModel(get()) }
    viewModel { ComicsListViewModel(get()) }
    viewModel { ForumListViewModel(get()) }
    viewModel { NewsListViewModel(get()) }
    viewModel { SeriesListViewModel(get()) }
    viewModel { SeriesDetailViewModel(get()) }
}

const val SERVER_URL = "SERVER_URL"

private fun createOkHttpClient(context: Context): OkHttpClient {
    val cacheSize = 10L * 1024L * 1024L
    return OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .cache(Cache(context.cacheDir, cacheSize))
        .build()
}

private fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
}
