package cz.kutner.comicsdb;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by lukas.kutner on 27.4.2015.
 */
public class ComicsListRVAdapter extends RecyclerView.Adapter<ComicsListRVAdapter.ComicsViewHolder> {

    public static class ComicsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView comicsName;
        TextView publishedDate;
        TextView rating;

        ComicsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            comicsName = (TextView) itemView.findViewById(R.id.comics_name);
            publishedDate = (TextView) itemView.findViewById(R.id.comics_published);
            rating = (TextView) itemView.findViewById(R.id.comics_rating);
        }
    }

    List<Comics> comics;

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
        ComicsViewHolder pvh = new ComicsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ComicsViewHolder comicsViewHolder, int i) {
        comicsViewHolder.comicsName.setText(comics.get(i).getName());
        comicsViewHolder.publishedDate.setText(comics.get(i).getPublished());
        comicsViewHolder.rating.setText(Utils.nvl(comics.get(i).getRating().toString(), ""));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
