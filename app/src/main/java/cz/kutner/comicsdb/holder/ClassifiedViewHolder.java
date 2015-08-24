package cz.kutner.comicsdb.holder;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.model.Classified;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.list_item_classified)
public class ClassifiedViewHolder extends ItemViewHolder<Classified> {
    @ViewId(R.id.classified_nick_icon)
    ImageView classifiedNickIcon;
    @ViewId(R.id.classified_nick)
    TextView classifiedNick;
    @ViewId(R.id.classified_category)
    TextView classifiedCategory;
    @ViewId(R.id.classified_time)
    TextView classifiedTime;
    @ViewId(R.id.classified_text)
    TextView classifiedText;

    public ClassifiedViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(Classified classified, PositionInfo positionInfo) {
        classifiedNick.setText(classified.getNick());
        classifiedTime.setText(classified.getTime());
        classifiedText.setText(Html.fromHtml(classified.getText()));
        Picasso.with(ComicsDBApplication.getContext()).load(classified.getIconUrl()).into(classifiedNickIcon);
        classifiedCategory.setText(classified.getCategory());
    }
}
