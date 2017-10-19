package cz.kutner.comicsdb.di

import android.content.Context
import android.net.ConnectivityManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.metrics.AddTrace
import com.google.gson.GsonBuilder
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.connector.*
import okhttp3.OkHttpClient
import org.koin.android.AndroidModule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



class KoinModule : AndroidModule() {
    @AddTrace(name = "initKoinModule")
    override fun context() =
            applicationContext {
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
    val classifiedService: ClassifiedService by lazy { retrofit.create(ClassifiedService::class.java) }
    val comicsDetailService: ComicsDetailService by lazy { retrofit.create(ComicsDetailService::class.java) }
    val comicsListService: ComicsListService by lazy { retrofit.create(ComicsListService::class.java) }
    val forumService: ForumService by lazy { retrofit.create(ForumService::class.java) }
    val newsService: NewsService by lazy { retrofit.create(NewsService::class.java) }
}