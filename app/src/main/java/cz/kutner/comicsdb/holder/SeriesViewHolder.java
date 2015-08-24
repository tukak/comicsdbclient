package cz.kutner.comicsdb.holder;

import android.view.View;
import android.widget.TextView;

import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.model.Series;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.list_item_series)
public class SeriesViewHolder extends ItemViewHolder<Series> {
    @ViewId(R.id.series_name)
    TextView seriesName;
    @ViewId(R.id.seriesNumberOfComicses)
    TextView seriesNumberOfComicses;

    public SeriesViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(Series series, PositionInfo positionInfo) {
        seriesName.setText(series.getName());
        seriesNumberOfComicses.setText(series.getNumberOfComicses().toString());
    }
}
