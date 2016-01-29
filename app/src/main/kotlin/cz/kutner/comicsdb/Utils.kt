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

    public fun logVisit(screenName: String, category: String, action: String?, contentName: String, contentType: String, contentId: String?) {
        val tracker = ComicsDBApplication.tracker
        tracker.setScreenName(screenName)
        tracker.send(HitBuilders.ScreenViewBuilder().build())
        tracker.send(HitBuilders.EventBuilder().setCategory(category).setAction(action).build())
        Answers.getInstance().logContentView(ContentViewEvent().putContentName(contentName).putContentType(contentType).putContentId(contentId))
    }

}
