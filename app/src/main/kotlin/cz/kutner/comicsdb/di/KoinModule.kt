package cz.kutner.comicsdb.di

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.authorDetail.AuthorDetailViewModel
import cz.kutner.comicsdb.authorList.AuthorListViewModel
import cz.kutner.comicsdb.classifiedList.ClassifiedListViewModel
import cz.kutner.comicsdb.comicsDetail.ComicsDetailViewModel
import cz.kutner.comicsdb.comicsList.ComicsListViewModel
import cz.kutner.comicsdb.forumList.ForumListViewModel
import cz.kutner.comicsdb.network.NetworkModule
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.newsList.NewsListViewModel
import cz.kutner.comicsdb.seriesDetail.SeriesDetailViewModel
import cz.kutner.comicsdb.seriesList.SeriesListViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import java.util.concurrent.TimeUnit


val koinModule = applicationContext {
    bean { createOkHttpClient(get()) }
    bean { RetrofitModule(get(), getProperty(SERVER_URL)) }
    bean { createFirebaseAnalytics(get()) }
    bean { NetworkModule(get()) }
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

private fun createOkHttpClient(androidApplication: Application): OkHttpClient {
    val cacheSize = 10L * 1024L * 1024L
    val cache = Cache(androidApplication.cacheDir, cacheSize)
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }
    return okHttpClient
}

private fun createFirebaseAnalytics(androidApplication: Application): FirebaseAnalytics {
    val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(
            androidApplication
        )
    }
    return firebaseAnalytics
}