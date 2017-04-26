package cz.kutner.comicsdb

import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.utils.Utils
import timber.log.Timber

class ComicsDBApplication : android.app.Application() {

    lateinit var retfofitModule: RetrofitModule
        get

    override fun onCreate() {
        super.onCreate()
        setupComponent()

        Utils.context = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupComponent() {
        retfofitModule = RetrofitModule(applicationContext.getString(R.string.url_comicsdb))
    }
}