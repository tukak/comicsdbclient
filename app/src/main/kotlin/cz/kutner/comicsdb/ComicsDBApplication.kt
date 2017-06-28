package cz.kutner.comicsdb

import cz.kutner.comicsdb.di.KoinModule
import cz.kutner.comicsdb.utils.Utils
import org.koin.android.KoinApplication
import timber.log.Timber

class ComicsDBApplication : KoinApplication(KoinModule::class) {

    override fun onCreate() {
        super.onCreate()

        Utils.context = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}