package cz.kutner.comicsdb.di

import android.content.Context
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.HitBuilders
import cz.kutner.comicsdb.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GoogleAnalyticsTracker(context: Context) : Tracker {

    val tracker: com.google.android.gms.analytics.Tracker by lazy {
        val analytics = GoogleAnalytics.getInstance(context).newTracker(context.getString(R.string.google_analytics_id))
        analytics.enableExceptionReporting(true)
        analytics
    }

    override fun logVisit(screenName: String, category: String?, action: String?) {
        tracker.setScreenName(screenName)
        tracker.send(HitBuilders.ScreenViewBuilder().build())
        logEvent(category = category, action = action)
    }

    override fun logEvent(category: String?, action: String?) {
        val eventBuilder = HitBuilders.EventBuilder()
        if (category != null) {
            eventBuilder.setCategory(category)
        }
        if (action != null) {
            eventBuilder.setAction(action)
        }
        tracker.send(eventBuilder.build())
    }

    @Provides
    @Singleton
    fun provideTracker(): Tracker {
        return this
    }
}
