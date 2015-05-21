package cz.kutner.comicsdb.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import cz.kutner.comicsdb.model.ForumEntry;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ForumRVAdapter extends RecyclerView.Adapter<ForumRVAdapter.ForumViewHolder> {
    private final String LOG_TAG = getClass().getSimpleName();

    public static class ForumViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();

        TextView nick;
        TextView time;
        TextView forum;
        TextView text;
        ImageView icon;

        ForumViewHolder(View itemView) {
            super(itemView);
            nick = (TextView) itemView.findViewById(R.id.forum_comment_nick);
            time = (TextView) itemView.findViewById(R.id.forum_comment_time);
            forum = (TextView) itemView.findViewById(R.id.forum_comment_forum);
            text = (TextView) itemView.findViewById(R.id.forum_comment_text);
            icon = (ImageView) itemView.findViewById(R.id.forum_nick_icon);
        }
    }

    private List<ForumEntry> entries;

    public ForumRVAdapter(List<ForumEntry> entries) {
        this.entries = entries;
    }

    public void setEntries(List<ForumEntry> entries) {
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
    public void onBindViewHolder(ForumViewHolder comicsViewHolder, int i) {
        comicsViewHolder.nick.setText(entries.get(i).getNick());
        comicsViewHolder.time.setText(entries.get(i).getTime());
        comicsViewHolder.text.setText(Html.fromHtml(entries.get(i).getText()));
        comicsViewHolder.icon.setImageBitmap(entries.get(i).getIcon());
        comicsViewHolder.forum.setText(entries.get(i).getForum());
    }

}
