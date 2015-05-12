package cz.kutner.comicsdb;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 12.5.2015.
 */


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class CommentsRVAdapter extends RecyclerView.Adapter<CommentsRVAdapter.CommentsViewHolder> {

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();

        TextView commentNameDate;
        TextView commentText;
        RatingBar commentRatingBar;
        ImageButton nickIcon;

        CommentsViewHolder(View itemView) {
            super(itemView);
            commentNameDate = (TextView) itemView.findViewById(R.id.commentNameDate);
            commentText = (TextView) itemView.findViewById(R.id.commentText);
            commentRatingBar = (RatingBar) itemView.findViewById(R.id.commentRatingBar);
            nickIcon = (ImageButton) itemView.findViewById(R.id.nickIcon);
        }
    }

    private List<Comment> comments;

    CommentsRVAdapter(List<Comment> comments) {
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
        commentsViewHolder.commentNameDate.setText(comments.get(i).getNick() + " - " + comments.get(i).getTime());
        commentsViewHolder.commentText.setText(comments.get(i).getText());
        commentsViewHolder.commentRatingBar.setRating(comments.get(i).getStars());
    }
}
