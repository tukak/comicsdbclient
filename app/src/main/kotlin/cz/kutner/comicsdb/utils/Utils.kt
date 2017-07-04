package cz.kutner.comicsdb.utils

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics

fun ImageView.loadUrl(url: String?) {
    Glide.with(context).load(url).into(this)
}

fun FirebaseAnalytics.logVisit(contentName: String? = null, contentType: String? = null, contentId: String? = null) {
    val bundle: Bundle = Bundle()
    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, contentId)
    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, contentName)
    this.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
}

object Utils {

    val COMICSDB_URL = "http://comicsdb.cz" //R.string.url_comicsdb

    fun fixUrl(url: String): String {
        if (!url.startsWith("http") && !url.startsWith("data")) {
            return COMICSDB_URL + url
        } else {
            return url
        }
    }

    fun fromHtml(text: String?): Spanned {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(text)
        }
    }
}
