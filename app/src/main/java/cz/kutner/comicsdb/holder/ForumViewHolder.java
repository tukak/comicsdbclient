package cz.kutner.comicsdb.holder;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.model.ForumEntry;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.list_item_forum)
public class ForumViewHolder extends ItemViewHolder<ForumEntry> {

    @ViewId(R.id.forum_nick_icon)
    ImageView forumNickIcon;
    @ViewId(R.id.forum_comment_nick)
    TextView forumCommentNick;
    @ViewId(R.id.forum_comment_forum)
    TextView forumCommentForum;
    @ViewId(R.id.forum_comment_time)
    TextView forumCommentTime;
    @ViewId(R.id.forum_comment_text)
    TextView forumCommentText;

    public ForumViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(ForumEntry forumEntry, PositionInfo positionInfo) {
        forumCommentNick.setText(forumEntry.getNick());
        forumCommentTime.setText(forumEntry.getTime());
        forumCommentText.setText(Html.fromHtml(forumEntry.getText()));
        Picasso.with(ComicsDBApplication.getContext()).load(forumEntry.getIconUrl()).into(forumNickIcon);
        forumCommentForum.setText(forumEntry.getForum());
    }
}
