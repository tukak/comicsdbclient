package cz.kutner.comicsdb.helpers

import android.content.Intent
import android.databinding.BindingAdapter
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cz.kutner.comicsdb.authorDetail.AuthorDetailActivity
import cz.kutner.comicsdb.main.MainActivity
import cz.kutner.comicsdb.seriesDetail.SeriesDetailActivity
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.utils.fromHtml
import cz.kutner.comicsdb.utils.loadUrl


@BindingAdapter("imageUrl")
fun bitmapSource(view: ImageView, uri: String?) {
    if (uri != null) {
        view.loadUrl(uri)
    }
}

@BindingAdapter("spannableSeries")
fun getSpannableSeries(textView: TextView, comics: ComicsDetail) {
    val series = comics.series
    val seriesString = SpannableStringBuilder()
    seriesString.append(series.name)
    seriesString.setSpan(SeriesClickableSpan(series.id), 0, series.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    textView.text = seriesString
}

@BindingAdapter("spannableAuthors")
fun getSpannableAuthors(textView: TextView, comics: ComicsDetail) {
    val authors = SpannableStringBuilder()
    for (author in comics.authors) {
        val formerLength = authors.length
        authors.append(author.role)
        authors.append(" ")
        authors.append(author.name.fromHtml())
        authors.append("\n")
        authors.setSpan(AuthorClickableSpan(author.id), formerLength + author.role.length + 1, formerLength + author.role.length + author.name.fromHtml().length + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    }
    textView.text = authors
}


class AuthorClickableSpan(val id: Int) : ClickableSpan() {
    override fun onClick(widget: View?) {
        if (widget != null) {
            val intent = Intent(widget.context, AuthorDetailActivity::class.java)
            intent.putExtra(Intent.EXTRA_UID, id)
            widget.context.startActivity(intent)
        }
    }
}

class SeriesClickableSpan(val id: Int) : ClickableSpan() {
    override fun onClick(widget: View?) {
        if (widget != null) {
            val intent = Intent(widget.context, SeriesDetailActivity::class.java)
            intent.putExtra(Intent.EXTRA_UID, id)
            widget.context.startActivity(intent)
        }
    }
}
