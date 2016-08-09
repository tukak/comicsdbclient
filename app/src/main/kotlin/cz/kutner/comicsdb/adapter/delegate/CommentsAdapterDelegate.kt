package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Comment
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.utils.loadUrl
import kotlinx.android.synthetic.main.list_item_comment.view.*


class CommentsAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>> {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder) {
        val vh = holder as CommentsViewHolder
        val comment = items[position] as Comment
        vh.commentNick.text = comment.nick
        vh.commentTime.text = comment.time
        vh.commentText.text = comment.text
        vh.commentRatingBar.rating = comment.stars!!.toFloat()
        vh.nickIcon.loadUrl(comment.iconUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return CommentsViewHolder(inflater.inflate(R.layout.list_item_comment, parent, false))
    }

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Comment
    }

    class CommentsViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var commentNick: TextView = itemView.commentNick
        internal var commentTime: TextView = itemView.commentTime
        internal var commentRatingBar: RatingBar = itemView.commentRatingBar
        internal var commentText: TextView = itemView.commentText
        internal var nickIcon: ImageView = itemView.nickIcon
    }
}