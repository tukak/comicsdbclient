package cz.kutner.comicsdb

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.squareup.leakcanary.LeakCanary
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.di.AndroidModule
import cz.kutner.comicsdb.di.DaggerNetComponent
import cz.kutner.comicsdb.di.NetComponent
import cz.kutner.comicsdb.di.NetModule
import io.fabric.sdk.android.Fabric

class ComicsDBApplication : android.app.Application() {

    public lateinit var netComponent: NetComponent
        get

    override fun onCreate() {
        super.onCreate()

        netComponent = DaggerNetComponent.builder().androidModule(AndroidModule(this)).netModule(NetModule(applicationContext.getString(R.string.url_comicsdb))).build()

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