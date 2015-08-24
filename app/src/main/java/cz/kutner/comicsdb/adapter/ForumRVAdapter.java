package cz.kutner.comicsdb.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.model.ForumEntry;

public class ForumRVAdapter extends RecyclerView.Adapter<ForumRVAdapter.ForumViewHolder> {


    public static class ForumViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.forum_nick_icon)
        ImageView forumNickIcon;
        @Bind(R.id.forum_comment_nick)
        TextView forumCommentNick;
        @Bind(R.id.forum_comment_forum)
        TextView forumCommentForum;
        @Bind(R.id.forum_comment_time)
        TextView forumCommentTime;
        @Bind(R.id.forum_comment_text)
        TextView forumCommentText;
        @Bind(R.id.card_view_comments)
        CardView cardViewComments;

        ForumViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private List<ForumEntry> entries;

    public ForumRVAdapter(List<ForumEntry> entries) {
        this.entries = entries;
    }


    @Override
    public int getItemCount() {
        return entries.size();
    }

    @Override
    public ForumViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_forum, viewGroup, false);
        return new ForumViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ForumViewHolder forumViewHolder, int i) {
        forumViewHolder.forumCommentNick.setText(entries.get(i).getNick());
        forumViewHolder.forumCommentTime.setText(entries.get(i).getTime());
        forumViewHolder.forumCommentText.setText(Html.fromHtml(entries.get(i).getText()));
        Picasso.with(ComicsDBApplication.getContext()).load(entries.get(i).getIconUrl()).into(forumViewHolder.forumNickIcon);
        forumViewHolder.forumCommentForum.setText(entries.get(i).getForum());
    }

}
