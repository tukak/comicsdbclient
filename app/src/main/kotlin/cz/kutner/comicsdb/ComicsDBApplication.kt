package cz.kutner.comicsdb

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.di.*
import cz.kutner.comicsdb.utils.Utils
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class ComicsDBApplication : android.app.Application() {

    lateinit var applicationComponent: ApplicationComponent
        get

    override fun onCreate() {
        super.onCreate()
        setupComponent()

        Fabric.with(this, Crashlytics.Builder().core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build())

        if (BuildConfig.DEBUG) {
            Picasso.with(applicationContext).setIndicatorsEnabled(true)
        }

        Utils.context = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .retrofitModule(RetrofitModule(applicationContext.getString(R.string.url_comicsdb)))
                .googleAnalyticsTracker(GoogleAnalyticsTracker())
                .build()
    }
}