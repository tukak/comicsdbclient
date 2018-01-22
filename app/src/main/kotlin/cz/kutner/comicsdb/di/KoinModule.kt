package cz.kutner.comicsdb.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
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
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val koinModule = applicationContext {
    provide { createOkHttpClient(androidApplication()) }
    provide { createRetrofitModule(get(), getProperty(SERVER_URL)) }
    provide { createFirebaseAnalytics(androidApplication()) }
    provide { createNetworkModule(androidApplication()) }
}

const val SERVER_URL = "SERVER_URL"

private fun createOkHttpClient(applicationContext: Context): OkHttpClient {
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(ChuckInterceptor(applicationContext))
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

private fun createNetworkModule(androidApplication: Application): NetworkModule =
    NetworkModule(androidApplication)

/*TODO přesunout*/
class NetworkModule(private val applicationContext: Context) {
    fun isConnected(): Boolean {
        val cm =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
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