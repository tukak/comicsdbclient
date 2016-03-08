package cz.kutner.comicsdb.adapter

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
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.AuthorDetailActivity
import cz.kutner.comicsdb.activity.ImageViewActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.activity.SeriesDetailActivity
import cz.kutner.comicsdb.utils.loadUrl
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.model.Series
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick

class ComicsDetailRVAdapter(private var comics: Comics) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CommentsViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var commentNick: TextView = itemView.find<TextView>(R.id.commentNick)
        internal var commentTime: TextView = itemView.find<TextView>(R.id.commentTime)
        internal var commentRatingBar: RatingBar = itemView.find<RatingBar>(R.id.commentRatingBar)
        internal var commentText: TextView = itemView.find<TextView>(R.id.commentText)
        internal var nickIcon: ImageView = itemView.find<ImageView>(R.id.nickIcon)
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

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return 0 //záznam komiksu
            else -> return 1 //komentář
        }
    }

    override fun getItemCount(): Int {
        return comics.comments.size + 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val listViewItemType = getItemViewType(i)
        var v: RecyclerView.ViewHolder = ComicsViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_comics_detail, viewGroup, false))
        when (listViewItemType) {
        //0 ->  //komiks, ale ten už máme
            1 -> v = CommentsViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_comment, viewGroup, false)) //komentar

        }
        return v
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (i == 0) {
            // zaznam komiksu
            val vh = viewHolder as ComicsViewHolder

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

            var authors = SpannableStringBuilder();

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
                var seriesString = SpannableStringBuilder();
                seriesString.append(series.name)
                seriesString.setSpan(SeriesClickableSpan(series.id), 0, series.name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                vh.series.text = seriesString
                vh.series.movementMethod = LinkMovementMethod.getInstance()
            }
            vh.cover.loadUrl(comics.coverUrl)
            vh.cover.onClick {
                val intent = Intent(vh.cover.context, ImageViewActivity::class.java)
                intent.putExtra(ImageViewActivity.IMAGE_URL, comics.fullCoverUrl)
                vh.cover.context.startActivity(intent)
            }
            vh.url.text = vh.url.context.getString(R.string.url_comics_detail) + comics.id.toString()
            vh.comicsDetailRatingBar.rating = Math.round(comics.rating.toFloat() / 20).toFloat()
        } else {
            val j = i - 1 //i je o 1 větší, tak to musíme zmenšit, kvůli poli
            val vh = viewHolder as CommentsViewHolder
            vh.commentNick.text = comics.comments[j].nick
            vh.commentTime.text = comics.comments[j].time
            vh.commentText.text = comics.comments[j].text
            vh.commentRatingBar.rating = comics.comments[j].stars!!.toFloat()
            vh.nickIcon.loadUrl(comics.comments[j].iconUrl)
        }
    }
}
