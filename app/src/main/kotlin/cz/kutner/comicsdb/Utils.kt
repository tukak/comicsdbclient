package cz.kutner.comicsdb

import android.content.Context
import android.net.ConnectivityManager
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker

object Utils {

    val tracker: Tracker by lazy {
        val analytics = GoogleAnalytics.getInstance(ComicsDBApplication.context).newTracker(ComicsDBApplication.context?.getString(R.string.google_analytics_id))
        analytics.enableExceptionReporting(true)
        analytics
    }

    fun isConnected(): Boolean {
        val cm = ComicsDBApplication.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun fixUrl(url: String, prefix: String = ""): String {
        val real_prefix = if (prefix.length > 0) prefix else ComicsDBApplication.context?.getString(R.string.url_comicsdb)
        if (!url.startsWith("http") && !url.startsWith("data")) {
            return real_prefix + url
        } else {
            return url
        }
    }

    fun logVisitToGoogleAnalytics(screenName: String, category: String? = null, action: String? = null) {
        tracker.setScreenName(screenName)
        tracker.send(HitBuilders.ScreenViewBuilder().build())
        logEventToGoogleAnalytics(category = category, action = action)
    }

    fun logEventToGoogleAnalytics(category: String?, action: String?) {
        val eventBuilder = HitBuilders.EventBuilder()
        if (category != null) {
            eventBuilder.setCategory(category)
        }
        if (action != null) {
            eventBuilder.setAction(action)
        }
        tracker.send(eventBuilder.build())
    }

    fun logVisitToFabricAnswers(contentName: String? = null, contentType: String? = null, contentId: String? = null) {
        val contentViewEvent = ContentViewEvent()
        if (contentName != null) {
            contentViewEvent.putContentName(contentName)
        }
        if (contentType != null) {
            contentViewEvent.putContentType(contentType)
        }
        if (contentId != null) {
            contentViewEvent.putContentId(contentId)
        }
        Answers.getInstance().logContentView(contentViewEvent)
    }

}
