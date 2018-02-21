package cz.kutner.comicsdb.di

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
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
import cz.kutner.comicsdb.utils.NetworkModule
import okhttp3.OkHttpClient
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val koinModule = applicationContext {
    provide { createOkHttpClient() }
    provide { createRetrofitModule(get(), getProperty(SERVER_URL)) }
    provide { createFirebaseAnalytics(get()) }
    provide { NetworkModule(get()) }
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

private fun createOkHttpClient(): OkHttpClient {
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    return okHttpClient
}

private fun createRetrofitModule(okHttpClient: OkHttpClient, baseUrl: String): RetrofitModule =
    RetrofitModule(okHttpClient, baseUrl)

private fun createFirebaseAnalytics(androidApplication: Application): FirebaseAnalytics {
    val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(
            androidApplication
        )
    }
    return firebaseAnalytics
}

/*TODO přesunout*/
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
    val seriesListService: SeriesListService by lazy { retrofit.create(SeriesListService::class.java) }
    val seriesDetailService: SeriesDetailService by lazy { retrofit.create(SeriesDetailService::class.java) }
    val authorDetailService: AuthorDetailService by lazy { retrofit.create(AuthorDetailService::class.java) }
    val authorListService: AuthorListService by lazy { retrofit.create(AuthorListService::class.java) }
    val classifiedListService: ClassifiedListService by lazy { retrofit.create(ClassifiedListService::class.java) }
    val comicsDetailService: ComicsDetailService by lazy { retrofit.create(ComicsDetailService::class.java) }
    val comicsListService: ComicsListService by lazy { retrofit.create(ComicsListService::class.java) }
    val forumListService: ForumListService by lazy { retrofit.create(ForumListService::class.java) }
    val newsListService: NewsListService by lazy { retrofit.create(NewsListService::class.java) }
}