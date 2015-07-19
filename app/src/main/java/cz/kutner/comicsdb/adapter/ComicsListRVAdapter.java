package cz.kutner.comicsdb.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cz.kutner.comicsdb.activity.ComicsDetailActivity;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class ComicsListRVAdapter extends RecyclerView.Adapter<ComicsListRVAdapter.ComicsViewHolder> {

    public static class ComicsViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();
        public final static String COMICS_ID = "cz.kutner.comicsdbclient.comicsdbclient.comics_id";

        @Bind(R.id.comics_name)
        TextView comicsName;
        @Bind(R.id.comics_published)
        TextView comicsPublished;
        @Bind(R.id.comics_rating)
        TextView comicsRating;
        Integer comicsId;


        ComicsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card_view_comics)
        public void onComicsClick(View v) {
            Intent intent = new Intent(v.getContext(), ComicsDetailActivity.class);
            intent.putExtra(COMICS_ID, comicsId);
            v.getContext().startActivity(intent);
        }
    }

    private List<Comics> comics;

    public ComicsListRVAdapter(List<Comics> comics) {
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
        comicsViewHolder.comicsPublished.setText(comics.get(i).getPublished());
        if (comics.get(i).getRating() > 0) {
            comicsViewHolder.comicsRating.setText(comics.get(i).getRating().toString());
        } else {
            comicsViewHolder.comicsRating.setText(" ");

        }
        comicsViewHolder.comicsId = comics.get(i).getId();
    }

}
