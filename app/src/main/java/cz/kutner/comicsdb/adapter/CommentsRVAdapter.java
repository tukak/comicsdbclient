package cz.kutner.comicsdb.adapter;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 12.5.2015.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import cz.kutner.comicsdb.model.Comment;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class CommentsRVAdapter extends RecyclerView.Adapter<CommentsRVAdapter.CommentsViewHolder> {

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();

        TextView commentNick;
        TextView commentTime;
        TextView commentText;
        RatingBar commentRatingBar;
        ImageView nickIcon;

        CommentsViewHolder(View itemView) {
            super(itemView);
            commentNick = (TextView) itemView.findViewById(R.id.commentNick);
            commentTime = (TextView) itemView.findViewById(R.id.commentTime);
            commentText = (TextView) itemView.findViewById(R.id.commentText);
            commentRatingBar = (RatingBar) itemView.findViewById(R.id.commentRatingBar);
            nickIcon = (ImageView) itemView.findViewById(R.id.nickIcon);
        }
    }

    private List<Comment> comments;

    public CommentsRVAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    public void setComicsList(List<Comment> comments) {
        this.comments = comments;
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_comment, viewGroup, false);
        return new CommentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder commentsViewHolder, int i) {
        commentsViewHolder.commentNick.setText(comments.get(i).getNick());
        commentsViewHolder.commentTime.setText(comments.get(i).getTime());
        commentsViewHolder.commentText.setText(comments.get(i).getText());
        commentsViewHolder.commentRatingBar.setRating(comments.get(i).getStars());
        commentsViewHolder.nickIcon.setImageBitmap(comments.get(i).getIcon());
    }
}
