package cz.kutner.comicsdb.holder

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.ForumEntry
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_forum)
public class ForumViewHolder(view: View) : ItemViewHolder<ForumEntry>(view) {

    @ViewId(R.id.forum_nick_icon)
    var forumNickIcon: ImageView? = null
    @ViewId(R.id.forum_comment_nick)
    var forumCommentNick: TextView? = null
    @ViewId(R.id.forum_comment_forum)
    var forumCommentForum: TextView? = null
    @ViewId(R.id.forum_comment_time)
    var forumCommentTime: TextView? = null
    @ViewId(R.id.forum_comment_text)
    var forumCommentText: TextView? = null

    override fun onSetValues(forumEntry: ForumEntry, positionInfo: PositionInfo) {
        forumCommentNick?.text = forumEntry.nick
        forumCommentTime?.text = forumEntry.time
        forumCommentText?.text = Html.fromHtml(forumEntry.text)
        Picasso.with(ComicsDBApplication.context).load(forumEntry.iconUrl).into(forumNickIcon)
        forumCommentForum?.text = forumEntry.forum
    }
}
