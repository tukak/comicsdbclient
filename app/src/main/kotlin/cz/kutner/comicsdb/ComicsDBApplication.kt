package cz.kutner.comicsdb

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.squareup.leakcanary.LeakCanary
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.di.*
import io.fabric.sdk.android.Fabric

class ComicsDBApplication : android.app.Application() {

    lateinit var applicationComponent: ApplicationComponent
        get

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .retrofitModule(RetrofitModule(applicationContext.getString(R.string.url_comicsdb)))
                .googleAnalyticsTracker(GoogleAnalyticsTracker(applicationContext))
                .build()

        Fabric.with(this, Crashlytics.Builder().core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build())
        if (BuildConfig.DEBUG) {
            Picasso.with(applicationContext).setIndicatorsEnabled(true)
        }
        LeakCanary.install(this)
        context = applicationContext
    }

    companion object {
        //TODO tady bych se rád zbavil těch otazníků
        var context: Context? = null
            set
    }
}