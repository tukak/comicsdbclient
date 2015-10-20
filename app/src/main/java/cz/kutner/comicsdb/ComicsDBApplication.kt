package cz.kutner.comicsdb

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.connector.converter.*
import cz.kutner.comicsdb.connector.service.*
import io.fabric.sdk.android.Fabric
import retrofit.RestAdapter
import timber.log.Timber

class ComicsDBApplication : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics.Builder().core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build())
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Picasso.with(applicationContext).setIndicatorsEnabled(true)
        }
        context = applicationContext
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
        val seriesService: SeriesService by lazy { RestAdapter.Builder().setEndpoint("http://www.comicsdb.cz").setConverter(SeriesConverter()).build().create(SeriesService::class.java) }
        val authorService: AuthorService by lazy { RestAdapter.Builder().setEndpoint("http://www.comicsdb.cz").setConverter(AuthorConverter()).build().create(AuthorService::class.java) }
        val newsService: NewsService by lazy { RestAdapter.Builder().setEndpoint("http://www.comicsdb.cz").setConverter(NewsConverter()).build().create(NewsService::class.java) }
        val forumService: ForumService by lazy { RestAdapter.Builder().setEndpoint("http://www.comicsdb.cz").setConverter(ForumConverter()).build().create(ForumService::class.java) }
        val classifiedService: ClassifiedService by lazy { RestAdapter.Builder().setEndpoint("http://www.comicsdb.cz").setConverter(ClassifiedConverter()).build().create(ClassifiedService::class.java) }
        val comicsListService: ComicsListService by lazy { RestAdapter.Builder().setEndpoint("http://www.comicsdb.cz").setConverter(ComicsListConverter()).build().create(ComicsListService::class.java) }
        val comicsService: ComicsService by lazy { RestAdapter.Builder().setEndpoint("http://www.comicsdb.cz").setConverter(ComicsConverter()).build().create(ComicsService::class.java) }
    }
}