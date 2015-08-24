package cz.kutner.comicsdb.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.model.NewsItem;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.NewsItemViewHolder> {

    public static class NewsItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.newsItemTitle)
        TextView newsItemTitle;
        @Bind(R.id.newsItemNick)
        TextView newsItemNick;
        @Bind(R.id.newsItemTime)
        TextView newsItemTime;
        @Bind(R.id.newsItemText)
        TextView newsItemText;

        NewsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            newsItemText.setMovementMethod(LinkMovementMethod.getInstance());
        }

    }

    private List<NewsItem> news;

    public NewsRVAdapter(List<NewsItem> news) {
        this.news = news;
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_news, viewGroup, false);
        return new NewsItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder newsItemViewHolder, int i) {
        newsItemViewHolder.newsItemNick.setText(news.get(i).getNick());
        newsItemViewHolder.newsItemText.setText(Html.fromHtml(news.get(i).getText()));
        newsItemViewHolder.newsItemTime.setText(news.get(i).getTime());
        newsItemViewHolder.newsItemTitle.setText(news.get(i).getTitle());
    }

}
