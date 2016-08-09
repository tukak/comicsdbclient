package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.utils.Utils
import cz.kutner.comicsdb.utils.loadUrl
import kotlinx.android.synthetic.main.list_item_forum.view.*


class ForumListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>> {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is ForumEntry
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder) {
        val vh: ForumEntryViewHolder = holder as ForumEntryViewHolder
        val forumEntry: ForumEntry = items[position] as ForumEntry
        vh.forumCommentNick.text = forumEntry.nick
        vh.forumCommentTime.text = forumEntry.time
        vh.forumCommentText.text = Utils.fromHtml(forumEntry.text)
        vh.forumNickIcon.loadUrl(forumEntry.iconUrl)
        vh.forumCommentForum.text = forumEntry.forum
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return ForumEntryViewHolder(inflater.inflate(R.layout.list_item_forum, parent, false))
    }

    internal class ForumEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var forumNickIcon: ImageView = itemView.forum_nick_icon
        var forumCommentNick: TextView  = itemView.forum_comment_nick
        var forumCommentForum: TextView  = itemView.forum_comment_forum
        var forumCommentTime: TextView = itemView.forum_comment_time
        var forumCommentText: TextView = itemView.forum_comment_text
    }

}