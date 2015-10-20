package cz.kutner.comicsdb.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Comics

class ComicsDetailRVAdapter(private var comics: Comics) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CommentsViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var commentNick: TextView = itemView.findViewById(R.id.commentNick) as TextView
        internal var commentTime: TextView = itemView.findViewById(R.id.commentTime) as TextView
        internal var commentRatingBar: RatingBar = itemView.findViewById(R.id.commentRatingBar) as RatingBar
        internal var commentText: TextView = itemView.findViewById(R.id.commentText) as TextView
        internal var nickIcon: ImageView = itemView.findViewById(R.id.nickIcon) as ImageView
    }

    class ComicsViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var cover: ImageView = itemView.findViewById(R.id.cover) as ImageView
        internal var name: TextView = itemView.findViewById(R.id.name) as TextView
        internal var genre: TextView = itemView.findViewById(R.id.genre) as TextView
        internal var comicsDetailRatingBar: RatingBar = itemView.findViewById(R.id.comics_detail_rating_bar) as RatingBar
        internal var rating: TextView = itemView.findViewById(R.id.rating) as TextView
        internal var publisher: TextView = itemView.findViewById(R.id.publisher) as TextView
        internal var pagesCount: TextView = itemView.findViewById(R.id.pagesCount) as TextView
        internal var price: TextView = itemView.findViewById(R.id.price) as TextView
        internal var originalName: TextView = itemView.findViewById(R.id.originalName) as TextView
        internal var binding: TextView = itemView.findViewById(R.id.binding) as TextView
        internal var series: TextView = itemView.findViewById(R.id.series) as TextView
        internal var issueNumber: TextView = itemView.findViewById(R.id.issueNumber) as TextView
        internal var format: TextView = itemView.findViewById(R.id.format) as TextView
        internal var url: TextView = itemView.findViewById(R.id.url) as TextView
        internal var description: TextView = itemView.findViewById(R.id.description) as TextView
        internal var notes: TextView = itemView.findViewById(R.id.notes) as TextView
        internal var authors: TextView = itemView.findViewById(R.id.authors) as TextView
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return 0 //záznam komiksu
            else -> return 1 //komentář
        }
    }

    override fun getItemCount(): Int {
        return comics.comments.size() + 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val listViewItemType = getItemViewType(i)
        var v: RecyclerView.ViewHolder = ComicsViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_comics_detail, viewGroup, false))
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
            if (comics.rating!!.toInt() > 0) {
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
            vh.authors.text = comics.authors
            vh.series.text = comics.series
            Picasso.with(ComicsDBApplication.context).load(comics.coverUrl).into(vh.cover)
            vh.url.text = ComicsDBApplication.context!!.getString(R.string.url_comics_detail) + comics.id.toString()
            vh.comicsDetailRatingBar.rating = Math.round(comics.rating!!.toFloat() / 20).toFloat()
        } else {
            val j = i-1 //i je o 1 větší, tak to musíme zmenšit, kvůli poli
            val vh = viewHolder as CommentsViewHolder
            vh.commentNick.text = comics.comments.get(j).nick
            vh.commentTime.text = comics.comments.get(j).time
            vh.commentText.text = comics.comments.get(j).text
            vh.commentRatingBar.rating = comics.comments.get(j).stars!!.toFloat()
            Picasso.with(ComicsDBApplication.context).load(comics.comments.get(j).iconUrl).into(vh.nickIcon)
        }
    }
}
