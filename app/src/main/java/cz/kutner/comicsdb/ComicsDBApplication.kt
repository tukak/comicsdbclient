package cz.kutner.comicsdb

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp.StethoInterceptor
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.squareup.okhttp.OkHttpClient
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.connector.converter.*
import cz.kutner.comicsdb.connector.service.*
import io.fabric.sdk.android.Fabric
import retrofit.RestAdapter
import retrofit.client.OkClient

class ComicsDBApplication : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics.Builder().core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build())
        if (BuildConfig.DEBUG) {
            Picasso.with(applicationContext).setIndicatorsEnabled(true)
        }
        context = applicationContext
        Stetho.initializeWithDefaults(this);

        client.networkInterceptors().add(StethoInterceptor());
    }

    companion object {
        //TODO tady bych se rád zbavil těch otazníků
        var context: Context? = null
            set
        val tracker: Tracker by lazy {
            val analytics = GoogleAnalytics.getInstance(context).newTracker(context?.getString(R.string.google_analytics_id))
            analytics.enableExceptionReporting(true)
            analytics
        }
        val client: OkHttpClient = OkHttpClient();

        val adapter: RestAdapter.Builder by lazy { RestAdapter.Builder().setEndpoint(context?.getString(R.string.url_comicsdb)).setClient(OkClient(client)) }
        val seriesService: SeriesService by lazy { adapter.setConverter(SeriesConverter()).build().create(SeriesService::class.java) }
        val seriesDetailService: SeriesDetailService by lazy { adapter.setConverter(SeriesDetailConverter()).build().create(SeriesDetailService::class.java) }
        val authorService: AuthorService by lazy { adapter.setConverter(AuthorConverter()).build().create(AuthorService::class.java) }
        val authorDetailService: AuthorDetailService by lazy { adapter.setConverter(AuthorDetailConverter()).build().create(AuthorDetailService::class.java) }
        val newsService: NewsService by lazy { adapter.setConverter(NewsConverter()).build().create(NewsService::class.java) }
        val forumService: ForumService by lazy { adapter.setConverter(ForumConverter()).build().create(ForumService::class.java) }
        val classifiedService: ClassifiedService by lazy { adapter.setConverter(ClassifiedConverter()).build().create(ClassifiedService::class.java) }
        val comicsListService: ComicsListService by lazy { adapter.setConverter(ComicsListConverter()).build().create(ComicsListService::class.java) }
        val comicsService: ComicsService by lazy { adapter.setConverter(ComicsConverter()).build().create(ComicsService::class.java) }
    }
}