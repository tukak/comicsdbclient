package cz.kutner.comicsdb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.model.Series;

public class SeriesRVAdapter extends RecyclerView.Adapter<SeriesRVAdapter.SeriesViewHolder> {


    public static class SeriesViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.series_name)
        TextView seriesName;
        @Bind(R.id.seriesNumberOfComicses)
        TextView seriesNumberOfComicses;

        SeriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private List<Series> series;

    public SeriesRVAdapter(List<Series> series) {
        this.series = series;
    }


    @Override
    public int getItemCount() {
        return series.size();
    }

    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_series, viewGroup, false);
        return new SeriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SeriesViewHolder seriesViewHolder, int i) {
        seriesViewHolder.seriesName.setText(series.get(i).getName());
        seriesViewHolder.seriesNumberOfComicses.setText(series.get(i).getNumberOfComicses().toString());
    }

}
