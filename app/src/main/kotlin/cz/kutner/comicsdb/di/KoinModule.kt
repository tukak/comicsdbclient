package cz.kutner.comicsdb.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.metrics.AddTrace
import com.google.gson.GsonBuilder
import cz.kutner.comicsdb.authorDetail.AuthorDetailService
import cz.kutner.comicsdb.authorList.AuthorListService
import cz.kutner.comicsdb.classified.ClassifiedListService
import cz.kutner.comicsdb.comicsDetail.ComicsDetailService
import cz.kutner.comicsdb.comicsList.ComicsListService
import cz.kutner.comicsdb.forumList.ForumListService
import cz.kutner.comicsdb.newsList.NewsListService
import cz.kutner.comicsdb.seriesDetail.SeriesDetailService
import cz.kutner.comicsdb.seriesList.SeriesListService
import okhttp3.OkHttpClient
import org.koin.android.module.AndroidModule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class KoinModule : AndroidModule() {
    @AddTrace(name = "initKoinModule")
    override fun context() =
            applicationContext {
                provide { createOkHttpClient() }
                provide { createRetrofitModule(get(), getProperty(KoinModule.SERVER_URL)) }
                provide { createFirebaseAnalytics() }
                provide { createNetworkModule() }
            }

    private fun createOkHttpClient(): OkHttpClient {
        val okHttpClient: OkHttpClient by lazy { OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).build() }
        return okHttpClient
    }

    private fun createRetrofitModule(okHttpClient: OkHttpClient, baseUrl: String): RetrofitModule {
        return RetrofitModule(okHttpClient, baseUrl)
    }

    private fun createFirebaseAnalytics(): FirebaseAnalytics {
        val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(androidApplication) }
        return firebaseAnalytics
    }

    private fun createNetworkModule(): NetworkModule {
        return NetworkModule(androidApplication)
    }

    companion object {
        const val SERVER_URL = "SERVER_URL"
    }
}

/*TODO přesunout*/
class NetworkModule(private val applicationContext: Context) {
    fun isConnected(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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