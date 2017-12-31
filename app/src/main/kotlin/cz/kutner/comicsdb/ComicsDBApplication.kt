package cz.kutner.comicsdb

import android.app.Application
import com.google.firebase.perf.metrics.AddTrace
import cz.kutner.comicsdb.di.SERVER_URL
import cz.kutner.comicsdb.di.koinModule
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
        }
    }
}