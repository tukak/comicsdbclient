package cz.kutner.comicsdb.utils

import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import androidx.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String) {
    if (url.isNotBlank()) Picasso.get().load(url).into(this)
}

fun FirebaseAnalytics.logVisit(contentName: String, contentType: String, contentId: String = "") {
    val bundle = bundleOf(
        FirebaseAnalytics.Param.ITEM_ID to contentId,
        FirebaseAnalytics.Param.CONTENT_TYPE to contentType,
        FirebaseAnalytics.Param.ITEM_NAME to contentName
    )
    this.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
}

fun String.fromHtml(): Spanned {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}