package cz.kutner.comicsdb.holder;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.activity.ComicsDetailActivity;
import cz.kutner.comicsdb.model.Comics;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.list_item_comics)
public class ComicsViewHolder extends ItemViewHolder<Comics> {
    public final static String COMICS_ID = "cz.kutner.comicsdbclient.comicsdbclient.comics_id";

    @ViewId(R.id.comics_name)
    TextView comicsName;
    @ViewId(R.id.comics_published)
    TextView comicsPublished;
    @ViewId(R.id.comics_rating)
    TextView comicsRating;
    Integer comicsId;

    public ComicsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.card_view_comics)
    public void onComicsClick(View v) {
        Intent intent = new Intent(v.getContext(), ComicsDetailActivity.class);
        intent.putExtra(COMICS_ID, comicsId);
        v.getContext().startActivity(intent);
    }

    @Override
    public void onSetValues(Comics comics, PositionInfo positionInfo) {
        comicsName.setText(comics.getName());
        comicsPublished.setText(comics.getPublished());
        if (comics.getRating() > 0) {
            comicsRating.setText(comics.getRating().toString());
        } else {
            comicsRating.setText(" ");
        }
        comicsId = comics.getId();
    }
}
