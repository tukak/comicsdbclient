package cz.kutner.comicsdb

import android.app.Application
import cz.kutner.comicsdb.di.KoinModule
import cz.kutner.comicsdb.utils.Utils
import org.koin.Koin
import org.koin.KoinContext
import org.koin.android.KoinContextAware
import org.koin.android.init
import timber.log.Timber

class ComicsDBApplication : Application(), KoinContextAware {

    lateinit var context: KoinContext

    override fun getKoin(): KoinContext = context

    override fun onCreate() {
        super.onCreate()

        Utils.context = applicationContext

        context = Koin().init(this).build(KoinModule())

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}