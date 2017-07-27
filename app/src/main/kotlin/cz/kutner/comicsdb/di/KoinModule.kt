package cz.kutner.comicsdb.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.connector.converter.*
import cz.kutner.comicsdb.connector.service.*
import okhttp3.OkHttpClient
import org.koin.android.AndroidModule
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class KoinModule : AndroidModule() {

    override fun context() =
            declareContext {
                provide { createOkHttpClient() }
                provide { createRetrofitModule(get(), resources.getString(R.string.url_comicsdb)) }
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
        val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(applicationContext) }
        return firebaseAnalytics
    }

    private fun createNetworkModule(): NetworkModule {
        return NetworkModule(applicationContext)
    }
}

class NetworkModule(val applicationContext: Context) {
    fun isConnected(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}

class RetrofitModule(okHttpClient: OkHttpClient, baseUrl: String) {
    val seriesListService: SeriesListService by lazy { Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(SeriesListConverterFactory.create()).build().create(SeriesListService::class.java) }
    val seriesDetailService: SeriesDetailService by lazy { Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(SeriesDetailConverterFactory.create()).build().create(SeriesDetailService::class.java) }
    val authorDetailService: AuthorDetailService by lazy { Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(AuthorDetailConverterFactory.create()).build().create(AuthorDetailService::class.java) }
    val authorListService: AuthorListService by lazy { Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(AuthorListConverterFactory.create()).build().create(AuthorListService::class.java) }
    val classifiedService: ClassifiedService by lazy { Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(ClassifiedConverterFactory.create()).build().create(ClassifiedService::class.java) }
    val comicsDetailService: ComicsDetailService by lazy { Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(ComicsDetailConverterFactory.create()).build().create(ComicsDetailService::class.java) }
    val comicsListService: ComicsListService by lazy { Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(ComicsListConverterFactory.create()).build().create(ComicsListService::class.java) }
    val forumService: ForumService by lazy { Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(ForumConverterFactory.create()).build().create(ForumService::class.java) }
    val newsService: NewsService by lazy { Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(NewsConverterFactory.create()).build().create(NewsService::class.java) }
}