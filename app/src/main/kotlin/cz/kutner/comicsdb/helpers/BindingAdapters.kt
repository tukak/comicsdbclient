package cz.kutner.comicsdb.helpers

import android.content.Intent
import android.databinding.BindingAdapter
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.parseAsHtml
import cz.kutner.comicsdb.authorDetail.AuthorDetailActivity
import cz.kutner.comicsdb.image.ImageViewSliderActivity
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.model.Image
import cz.kutner.comicsdb.seriesDetail.SeriesDetailActivity
import cz.kutner.comicsdb.utils.PicassoImageGetter
import cz.kutner.comicsdb.utils.loadUrl
import java.util.*


@BindingAdapter("imageUrl")
fun bitmapSource(view: ImageView, uri: String?) {
    if (uri != null) {
        view.loadUrl(uri)
    }
}

@BindingAdapter("htmlWithImage")
fun htmlWithImage(textView: TextView, string: String) {
    val html: Spannable =
        string.parseAsHtml(imageGetter = PicassoImageGetter(textView)) as Spannable
    for (span in html.getSpans(0, html.length, ImageSpan::class.java)) {
        val flags = html.getSpanFlags(span)
        val start = html.getSpanStart(span)
        val end = html.getSpanEnd(span)
        html.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                val allImages: ArrayList<Image> = ArrayList()
                allImages.add(Image(span.source, span.source, "Obrázek"))
                val intent = Intent(view.context, ImageViewSliderActivity::class.java)
                intent.putParcelableArrayListExtra(ImageViewSliderActivity.IMAGES, allImages)
                intent.putExtra(ImageViewSliderActivity.POSTITION, 0)
                view.context.startActivity(intent)
            }
        }, start, end, flags)
    }
    textView.text = html
    textView.isClickable = true
    textView.movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter("spannableSeries")
fun getSpannableSeries(textView: TextView, comics: ComicsDetail) {
    val series = comics.series
    val seriesString = SpannableStringBuilder()
    seriesString.append(series.name)
    seriesString.setSpan(
        SeriesClickableSpan(series.id),
        0,
        series.name.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    textView.text = seriesString
}

@BindingAdapter("spannableAuthors")
fun getSpannableAuthors(textView: TextView, comics: ComicsDetail) {
    val authors = SpannableStringBuilder()
    for (author in comics.authors) {
        val formerLength = authors.length
        authors.append(author.role)
        authors.append(" ")
        authors.append(author.name.parseAsHtml())
        authors.append("\n")
        authors.setSpan(
            AuthorClickableSpan(author.id),
            formerLength + author.role.length + 1,
            formerLength + author.role.length + author.name.parseAsHtml().length + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

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
