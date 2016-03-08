package cz.kutner.comicsdb.holder

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.loadUrl
import cz.kutner.comicsdb.model.ForumEntry
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_forum) class ForumViewHolder(view: View) : ItemViewHolder<ForumEntry>(view) {

    @ViewId(R.id.forum_nick_icon)
    lateinit var forumNickIcon: ImageView
    @ViewId(R.id.forum_comment_nick)
    lateinit var forumCommentNick: TextView
    @ViewId(R.id.forum_comment_forum)
    lateinit var forumCommentForum: TextView
    @ViewId(R.id.forum_comment_time)
    lateinit var forumCommentTime: TextView
    @ViewId(R.id.forum_comment_text)
    lateinit var forumCommentText: TextView

    override fun onSetValues(forumEntry: ForumEntry, positionInfo: PositionInfo) {
        forumCommentNick.text = forumEntry.nick
        forumCommentTime.text = forumEntry.time
        forumCommentText.text = Html.fromHtml(forumEntry.text)
        forumNickIcon.loadUrl(forumEntry.iconUrl)
        forumCommentForum.text = forumEntry.forum
    }
}
