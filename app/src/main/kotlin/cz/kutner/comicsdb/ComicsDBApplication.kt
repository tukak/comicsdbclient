package cz.kutner.comicsdb

import android.app.Application
import com.google.firebase.perf.metrics.AddTrace
import cz.kutner.comicsdb.di.KoinModule
import org.koin.android.KoinContextAware
import org.koin.android.newKoinContext
import timber.log.Timber

class ComicsDBApplication : Application(), KoinContextAware {

    override val koinContext = newKoinContext(this, KoinModule())

    @AddTrace(name = "onCreateApplication")
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}