package cz.kutner.comicsdb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String?) {
    Picasso.with(context).load(url).into(this)
}

fun ImageView.loadUrlFullScreen(url: String?) {
    Picasso.with(context).load(url).fit().into(this)
}
object Utils {

    val COMICSDB_URL = "http://comicsdb.cz" //R.string.url_comicsdb

    lateinit var context: Context
        set

    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun fixUrl(url: String): String {
        if (!url.startsWith("http") && !url.startsWith("data")) {
            return COMICSDB_URL + url
        } else {
            return url
        }
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
