package cz.kutner.comicsdb

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.AddTrace
import com.squareup.leakcanary.LeakCanary
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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        startKoin(this, listOf(koinModule))
        bindString(R.string.url_comicsdb, SERVER_URL)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Fabric.with(this, Crashlytics())
            FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(true)
            FirebasePerformance.getInstance().isPerformanceCollectionEnabled = true
        }
    }
}