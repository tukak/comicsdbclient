package cz.kutner.comicsdb.helpers

import android.content.Intent
import android.databinding.BindingAdapter
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cz.kutner.comicsdb.activity.AuthorDetailActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.activity.SeriesDetailActivity
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.utils.loadUrl


@BindingAdapter("imageUrl")
fun bitmapSource(view: ImageView, uri: String) {
    view.loadUrl(uri)
}

@BindingAdapter("spannableSeries")
fun getSpannableSeries(textView: TextView, comics: Comics) {
    val series = comics.series
    if (series != null) {
        val seriesString = SpannableStringBuilder()
        seriesString.append(series.name)
        seriesString.setSpan(SeriesClickableSpan(series.id), 0, series.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = seriesString
    }
}

@BindingAdapter("spannableAuthors")
fun getSpannableAuthors(textView: TextView, comics: Comics) {
    val authors = SpannableStringBuilder()
    for (author in comics.authors) {
        val formerLength = authors.length
        authors.append(author.role)
        authors.append(" ")
        authors.append(author.name)
        authors.append("\n")
        if (author.id != null && author.name != null) {
            authors.setSpan(AuthorClickableSpan(author.id), formerLength + (author.role?.length ?: 0) + 1, formerLength + (author.role?.length ?: 0) + author.name.length + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
    textView.text = authors
}


class AuthorClickableSpan(id: Int) : ClickableSpan() {
    private var id: Int = 0

    init {
        this.id = id
    }

    override fun onClick(widget: View?) {
        if (widget != null) {
            val intent = Intent(widget.context, AuthorDetailActivity::class.java)
            intent.putExtra(MainActivity.AUTHOR_ID, id)
            widget.context.startActivity(intent)
        }
    }
}

class SeriesClickableSpan(id: Int) : ClickableSpan() {
    private var id: Int = 0

    init {
        this.id = id
    }

    override fun onClick(widget: View?) {
        if (widget != null) {
            val intent = Intent(widget.context, SeriesDetailActivity::class.java)
            intent.putExtra(MainActivity.SERIES_ID, id)
            widget.context.startActivity(intent)
        }
    }
}
