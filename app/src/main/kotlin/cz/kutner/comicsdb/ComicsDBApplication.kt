package cz.kutner.comicsdb

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.AddTrace
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.di.SERVER_URL
import cz.kutner.comicsdb.di.koinModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.bindString
import org.koin.android.ext.android.startKoin
import timber.log.Timber


class ComicsDBApplication : Application() {

    @AddTrace(name = "onCreateApplication")
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(koinModule))
        bindString(R.string.url_comicsdb, SERVER_URL)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Picasso.get().setIndicatorsEnabled(true)
            Picasso.get().isLoggingEnabled = true
        } else {
            Fabric.with(this, Crashlytics())
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
            FirebasePerformance.getInstance().isPerformanceCollectionEnabled = true
        }
    }
}