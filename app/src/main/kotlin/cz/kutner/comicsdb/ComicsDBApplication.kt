package cz.kutner.comicsdb

import android.app.Application
import com.google.firebase.perf.metrics.AddTrace
import cz.kutner.comicsdb.di.KoinModule
import org.koin.Koin
import org.koin.KoinContext
import org.koin.android.KoinContextAware
import org.koin.android.init
import timber.log.Timber

class ComicsDBApplication : Application(), KoinContextAware {

    lateinit var context: KoinContext

    override fun getKoin(): KoinContext = context
    @AddTrace(name = "onCreateApplication")
    override fun onCreate() {
        super.onCreate()

        context = Koin().init(this).build(KoinModule())

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}