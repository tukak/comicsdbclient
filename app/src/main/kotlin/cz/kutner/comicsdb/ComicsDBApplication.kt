package cz.kutner.comicsdb

import android.app.Application
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.di.SERVER_URL
import cz.kutner.comicsdb.di.koinModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber


class ComicsDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(koinModule), extraProperties = mapOf(Pair(SERVER_URL, "https://comicsdb.cz")))
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Picasso.get().setIndicatorsEnabled(true)
            Picasso.get().isLoggingEnabled = true
        }
    }
}