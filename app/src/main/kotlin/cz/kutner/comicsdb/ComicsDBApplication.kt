package cz.kutner.comicsdb

import android.app.Application
import com.google.firebase.perf.metrics.AddTrace
import cz.kutner.comicsdb.di.KoinModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.bindString
import org.koin.android.ext.koin.startAndroidContext
import timber.log.Timber

class ComicsDBApplication : Application() {

    @AddTrace(name = "onCreateApplication")
    override fun onCreate() {
        super.onCreate()
        startAndroidContext(this, KoinModule())
        getKoin().bindString(R.string.url_comicsdb, KoinModule.SERVER_URL)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}