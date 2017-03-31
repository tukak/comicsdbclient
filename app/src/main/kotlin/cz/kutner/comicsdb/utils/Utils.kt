package cz.kutner.comicsdb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics

fun ImageView.loadUrl(url: String?) {
    Glide.with(context).load(url).fitCenter().into(this)
}

object Utils {

    val COMICSDB_URL = "http://comicsdb.cz" //R.string.url_comicsdb

    lateinit var context: Context
        set

    val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

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

    fun logVisit(contentName: String? = null, contentType: String? = null, contentId: String? = null) {
        val bundle: Bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, contentId)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, contentName)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
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
