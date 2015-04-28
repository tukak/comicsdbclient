package cz.kutner.comicsdb;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class ComicsListRVAdapter extends RecyclerView.Adapter<ComicsListRVAdapter.ComicsViewHolder> {

    public static class ComicsViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = ComicsViewHolder.class.getSimpleName();
        public final static String COMICS_URL = "cz.kutner.comicsdbclient.comicsdbclient.comics_url";

        CardView cv;
        TextView comicsName;
        TextView publishedDate;
        TextView rating;
        String Url;

        ComicsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            comicsName = (TextView) itemView.findViewById(R.id.comics_name);
            publishedDate = (TextView) itemView.findViewById(R.id.comics_published);
            rating = (TextView) itemView.findViewById(R.id.comics_rating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ComicsDetailActivity.class);
                    intent.putExtra(COMICS_URL, Url);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    private List<Comics> comics;

    ComicsListRVAdapter(List<Comics> comics) {
        this.comics = comics;
    }

    public void setComicsList(List<Comics> comics) {
        this.comics = comics;
    }


    @Override
    public int getItemCount() {
        return comics.size();
    }

    @Override
    public ComicsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_comics, viewGroup, false);
        return new ComicsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComicsViewHolder comicsViewHolder, int i) {
        comicsViewHolder.comicsName.setText(comics.get(i).getName());
        comicsViewHolder.publishedDate.setText(comics.get(i).getPublished());
        if (comics.get(i).getRating() > 0) {
            comicsViewHolder.rating.setText(comics.get(i).getRating().toString());
        } else {
            comicsViewHolder.rating.setText(" ");

        }
        comicsViewHolder.Url = comics.get(i).getUrl();
    }

}
