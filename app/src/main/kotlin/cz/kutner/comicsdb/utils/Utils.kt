package cz.kutner.comicsdb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String?) {
    Picasso.with(context).load(url).into(this)
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

    @Suppress("DEPRECATION")
    fun fromHtml(text: String?): Spanned {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(text,Html.FROM_HTML_MODE_LEGACY)
        } else {
            return Html.fromHtml(text)
        }
    }
}
