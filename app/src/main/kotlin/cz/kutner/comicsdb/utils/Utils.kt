package cz.kutner.comicsdb.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String) {
    if (url.isNotBlank()) Picasso.get().load(url).into(this)
}