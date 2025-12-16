package cz.kutner.comicsdb.utils

import android.widget.ImageView
import coil3.load

fun ImageView.loadUrl(url: String) {
    if (url.isNotBlank()) load(url)
}
