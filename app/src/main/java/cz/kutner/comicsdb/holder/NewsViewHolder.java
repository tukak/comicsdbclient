package cz.kutner.comicsdb.holder;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.model.NewsItem;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.list_item_news)
public class NewsViewHolder extends ItemViewHolder<NewsItem> {
    @ViewId(R.id.newsItemTitle)
    TextView newsItemTitle;
    @ViewId(R.id.newsItemNick)
    TextView newsItemNick;
    @ViewId(R.id.newsItemTime)
    TextView newsItemTime;
    @ViewId(R.id.newsItemText)
    TextView newsItemText;

    public NewsViewHolder(View view) {
        super(view);
        newsItemText.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public void onSetValues(NewsItem newsItem, PositionInfo positionInfo) {
        newsItemNick.setText(newsItem.getNick());
        newsItemText.setText(Html.fromHtml(newsItem.getText()));
        newsItemTime.setText(newsItem.getTime());
        newsItemTitle.setText(newsItem.getTitle());
    }
}
