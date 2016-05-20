package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.AuthorDetailActivity
import cz.kutner.comicsdb.activity.ImageViewActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.activity.SeriesDetailActivity
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.model.Series
import cz.kutner.comicsdb.utils.loadUrl
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick


class ComicsAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>> {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder) {
        val vh = holder as ComicsViewHolder
        val comics = items[position] as Comics
        vh.name.text = comics.name
        if (comics.rating.toInt() > 0) {
            vh.rating.text = "${comics.rating.toString()}% (${comics.voteCount.toString()})"
        } else {
            vh.rating.text = "< 5 hodnocení"
        }
        vh.genre.text = comics.genre ?: ""
        vh.publisher.text = "${comics.publisher} - ${comics.published}"
        vh.issueNumber.text = "Vydání: ${comics.issueNumber ?: ""} tisk: ${comics.print ?: ""}"
        vh.binding.text = "Vazba: ${comics.binding ?: ""}"
        vh.format.text = "Formát: ${comics.format ?: ""}"
        vh.pagesCount.text = "Počet stran: ${comics.pagesCount ?: ""}"
        if (comics.originalName != null) {
            vh.originalName.text = "Původně: ${comics.originalName}"
            if (comics.originalPublisher != null) {
                vh.originalName.text = "${vh.originalName.text as String} - ${comics.originalPublisher}"
            }
        } else {
            vh.originalName.text = ""
        }
        vh.price.text = "Cena: ${comics.price ?: ""}"
        if (comics.notes != null) {
            vh.notes.text = Html.fromHtml(comics.notes)
        }
        if (comics.description != null) {
            vh.description.text = Html.fromHtml(comics.description)
        }

        val authors = SpannableStringBuilder();

        for (author in comics.authors) {
            val formerLength = authors.length
            authors.append(author.role)
            authors.append(" ")
            authors.append(author.name)
            authors.append("\n")
            if (author.id != null && author.name != null) {
                authors.setSpan(AuthorClickableSpan(author.id), formerLength + (author.role?.length ?: 0) + 1, formerLength + (author.role?.length ?: 0) + author.name.length + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        vh.authors.text = authors
        vh.authors.movementMethod = LinkMovementMethod.getInstance()
        val series: Series? = comics.series
        if (series != null) {
            val seriesString = SpannableStringBuilder();
            seriesString.append(series.name)
            seriesString.setSpan(SeriesClickableSpan(series.id), 0, series.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            vh.series.text = seriesString
            vh.series.movementMethod = LinkMovementMethod.getInstance()
        }
        vh.cover.loadUrl(comics.cover.previewUrl)
        vh.cover.onClick {
            val intent = Intent(vh.cover.context, ImageViewActivity::class.java)
            intent.putExtra(ImageViewActivity.IMAGE_URL, comics.cover.fullUrl)
            vh.cover.context.startActivity(intent)
        }
        vh.url.text = vh.url.context.getString(R.string.url_comics_detail) + comics.id.toString()
        vh.comicsDetailRatingBar.rating = Math.round(comics.rating.toFloat() / 20).toFloat()
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return ComicsViewHolder(inflater.inflate(R.layout.fragment_comics_detail, parent, false))
    }

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Comics
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

    class ComicsViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var cover: ImageView = itemView.find<ImageView>(R.id.cover)
        internal var name: TextView = itemView.find<TextView>(R.id.name)
        internal var genre: TextView = itemView.find<TextView>(R.id.genre)
        internal var comicsDetailRatingBar: RatingBar = itemView.find<RatingBar>(R.id.comics_detail_rating_bar)
        internal var rating: TextView = itemView.find<TextView>(R.id.rating)
        internal var publisher: TextView = itemView.find<TextView>(R.id.publisher)
        internal var pagesCount: TextView = itemView.find<TextView>(R.id.pagesCount)
        internal var price: TextView = itemView.find<TextView>(R.id.price)
        internal var originalName: TextView = itemView.find<TextView>(R.id.originalName)
        internal var binding: TextView = itemView.find<TextView>(R.id.binding)
        internal var series: TextView = itemView.find<TextView>(R.id.series)
        internal var issueNumber: TextView = itemView.find<TextView>(R.id.issueNumber)
        internal var format: TextView = itemView.find<TextView>(R.id.format)
        internal var url: TextView = itemView.find<TextView>(R.id.url)
        internal var description: TextView = itemView.find<TextView>(R.id.description)
        internal var notes: TextView = itemView.find<TextView>(R.id.notes)
        internal var authors: TextView = itemView.find<TextView>(R.id.authors)
    }
}