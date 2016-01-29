package cz.kutner.comicsdb

import android.content.Context
import android.net.ConnectivityManager
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.android.gms.analytics.HitBuilders

public object Utils {

    public fun isConnected(): Boolean {
        val cm = ComicsDBApplication.context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    public fun fixUrl(url: String, prefix: String = ""): String {
        val real_prefix = if (prefix.length > 0) prefix else ComicsDBApplication.context?.getString(R.string.url_comicsdb)
        if (!url.startsWith("http") && !url.startsWith("data")) {
            return real_prefix + url
        } else {
            return url
        }
    }

    public fun logVisitToGoogleAnalytics(screenName: String, category: String? = null, action: String? = null) {
        val tracker = ComicsDBApplication.tracker
        tracker.setScreenName(screenName)
        tracker.send(HitBuilders.ScreenViewBuilder().build())
        val eventBuilder = HitBuilders.EventBuilder()
        if (category != null) {
            eventBuilder.setCategory(category)
        }
        if (action != null) {
            eventBuilder.setAction(action)
        }
        tracker.send(eventBuilder.build())
    }

    public fun logVisitToFabricAnswers(contentName: String? = null, contentType: String? = null, contentId: String? = null) {
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
