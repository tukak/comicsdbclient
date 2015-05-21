package cz.kutner.comicsdb.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cz.kutner.comicsdb.model.Classified;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ClassifiedRVAdapter extends RecyclerView.Adapter<ClassifiedRVAdapter.ClassifiedViewHolder> {
    private final String LOG_TAG = getClass().getSimpleName();

    public static class ClassifiedViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();

        TextView nick;
        TextView time;
        TextView category;
        TextView text;
        ImageView icon;

        ClassifiedViewHolder(View itemView) {
            super(itemView);
            nick = (TextView) itemView.findViewById(R.id.classified_nick);
            time = (TextView) itemView.findViewById(R.id.classified_time);
            category = (TextView) itemView.findViewById(R.id.classified_category);
            text = (TextView) itemView.findViewById(R.id.classified_text);
            icon = (ImageView) itemView.findViewById(R.id.classified_nick_icon);
        }
    }

    private List<Classified> entries;

    public ClassifiedRVAdapter(List<Classified> entries) {
        this.entries = entries;
    }

    public void setEntries(List<Classified> entries) {
        this.entries = entries;
    }


    @Override
    public int getItemCount() {
        return entries.size();
    }

    @Override
    public ClassifiedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_classified, viewGroup, false);
        return new ClassifiedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClassifiedViewHolder classifiedViewHolder, int i) {
        classifiedViewHolder.nick.setText(entries.get(i).getNick());
        classifiedViewHolder.time.setText(entries.get(i).getTime());
        classifiedViewHolder.text.setText(Html.fromHtml(entries.get(i).getText()));
        classifiedViewHolder.icon.setImageBitmap(entries.get(i).getIcon());
        classifiedViewHolder.category.setText(entries.get(i).getCategory());
    }

}
