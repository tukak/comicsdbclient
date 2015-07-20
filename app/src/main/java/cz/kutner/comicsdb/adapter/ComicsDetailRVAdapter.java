package cz.kutner.comicsdb.adapter;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 12.5.2015.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.model.Comics;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class ComicsDetailRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String LOG_TAG = getClass().getSimpleName();

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();

        @Bind(R.id.commentNick)
        TextView commentNick;
        @Bind(R.id.commentTime)
        TextView commentTime;
        @Bind(R.id.commentRatingBar)
        RatingBar commentRatingBar;
        @Bind(R.id.commentText)
        TextView commentText;
        @Bind(R.id.nickIcon)
        ImageView nickIcon;

        CommentsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ComicsViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();
        @Bind(R.id.cover)
        ImageView cover;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.genre)
        TextView genre;
        @Bind(R.id.comics_detail_rating_bar)
        RatingBar comicsDetailRatingBar;
        @Bind(R.id.rating)
        TextView rating;
        @Bind(R.id.publisher)
        TextView publisher;
        @Bind(R.id.pagesCount)
        TextView pagesCount;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.originalName)
        TextView originalName;
        @Bind(R.id.binding)
        TextView binding;
        @Bind(R.id.series)
        TextView series;
        @Bind(R.id.issueNumber)
        TextView issueNumber;
        @Bind(R.id.format)
        TextView format;
        @Bind(R.id.url)
        TextView url;
        @Bind(R.id.description)
        TextView description;
        @Bind(R.id.notes)
        TextView notes;
        @Bind(R.id.authors)
        TextView authors;

        ComicsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Comics comics;
    private Context context;

    public ComicsDetailRVAdapter(Comics comics, Context context) {
        this.comics = comics;
        this.context = context;
    }

    @DebugLog
    public void setComics(Comics comics) {
        this.comics = comics;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) { // záznam komiksu
            return 0;
        } else {
            return 1; // komentář
        }
    }

    @DebugLog
    @Override
    public int getItemCount() {
        return comics.getComments().size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int listViewItemType = getItemViewType(i);
        RecyclerView.ViewHolder v = null;
        switch (listViewItemType) {
            case 0:
                v = new ComicsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_comics_detail, viewGroup, false));
                break; //komiks
            case 1:
                v = new CommentsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_comment, viewGroup, false));
                break; //komentar
        }
        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (i == 0) { // zaznam komiksu
            ComicsViewHolder vh = (ComicsViewHolder) viewHolder;

            vh.name.setText(comics.getName());
            if (comics.getRating() > 0) {
                vh.rating.setText(comics.getRating().toString() + "% (" + comics.getVoteCount().toString() + ")");
            } else {
                vh.rating.setText("< 5 hodnocení");
            }
            vh.genre.setText(Utils.nvl(comics.getGenre(), ""));
            vh.publisher.setText(comics.getPublisher() + " - " + comics.getPublished());
            vh.issueNumber.setText("Vydání: " + Utils.nvl(comics.getIssueNumber(), "") + " tisk: " + Utils.nvl(comics.getPrint(), ""));
            vh.binding.setText("Vazba: " + Utils.nvl(comics.getBinding(), ""));
            vh.format.setText("Formát: " + Utils.nvl(comics.getFormat(), ""));
            vh.pagesCount.setText("Počet stran: " + Utils.nvl(comics.getPagesCount(), ""));
            if (comics.getOriginalName() != null) {
                vh.originalName.setText("Původně: " + comics.getOriginalName());
                if (comics.getOriginalPublisher() != null) {
                    vh.originalName.setText(vh.originalName.getText() + " - " + comics.getOriginalPublisher());
                }
            } else {
                vh.originalName.setText("");
            }
            vh.price.setText("Cena: " + Utils.nvl(comics.getPrice(), ""));
            if (!(comics.getNotes() == null)) {
                vh.notes.setText(Html.fromHtml(comics.getNotes()));
            }
            if (!(comics.getDescription() == null)) {
                vh.description.setText(Html.fromHtml(comics.getDescription()));
            }
            vh.authors.setText(comics.getAuthors());
            vh.series.setText(comics.getSeries());
            vh.cover.setImageBitmap(comics.getCover());
            vh.url.setText(context.getResources().getString(R.string.url_comics_detail) + comics.getId().toString());
            vh.comicsDetailRatingBar.setRating(Math.round((float) comics.getRating() / 20));
        } else {
            i--; //i je o 1 větší, tak to musíme zmenšit, kvůli poli
            CommentsViewHolder vh = (CommentsViewHolder) viewHolder;
            vh.commentNick.setText(comics.getComments().get(i).getNick());
            vh.commentTime.setText(comics.getComments().get(i).getTime());
            vh.commentText.setText(comics.getComments().get(i).getText());
            vh.commentRatingBar.setRating(comics.getComments().get(i).getStars());
            vh.nickIcon.setImageBitmap(comics.getComments().get(i).getIcon());
        }
    }
}
