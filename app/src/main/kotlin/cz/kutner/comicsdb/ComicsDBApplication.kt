package cz.kutner.comicsdb

import android.app.Application
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.di.SERVER_URL
import cz.kutner.comicsdb.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber


class ComicsDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger(Level.ERROR)

            // use the Android context given there
            androidContext(this@ComicsDBApplication)

            properties( mapOf (SERVER_URL to "https://comicsdb.cz") )

            modules(koinModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Picasso.get().setIndicatorsEnabled(true)
            Picasso.get().isLoggingEnabled = true
        }
    }
}