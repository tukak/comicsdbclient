package cz.kutner.comicsdb.adapter;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 12.5.2015.
 */


import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class ComicsDetailRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String LOG_TAG = getClass().getSimpleName();

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();

        TextView commentNick;
        TextView commentTime;
        TextView commentText;
        RatingBar commentRatingBar;
        ImageView nickIcon;

        CommentsViewHolder(View itemView) {
            super(itemView);
            commentNick = (TextView) itemView.findViewById(R.id.commentNick);
            commentTime = (TextView) itemView.findViewById(R.id.commentTime);
            commentText = (TextView) itemView.findViewById(R.id.commentText);
            commentRatingBar = (RatingBar) itemView.findViewById(R.id.commentRatingBar);
            nickIcon = (ImageView) itemView.findViewById(R.id.nickIcon);
        }
    }

    public static class ComicsViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();
        TextView name;
        TextView rating;
        TextView description;
        TextView genre;
        TextView publisher;
        TextView issueNumber;
        TextView binding;
        TextView format;
        TextView pagesCount;
        TextView originalName;
        TextView price;
        TextView notes;
        TextView authors;
        TextView series;
        ImageView cover;
        TextView URL;

        ComicsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            rating = (TextView) itemView.findViewById(R.id.rating);
            description = (TextView) itemView.findViewById(R.id.description);
            genre = (TextView) itemView.findViewById(R.id.genre);
            publisher = (TextView) itemView.findViewById(R.id.publisher);
            issueNumber = (TextView) itemView.findViewById(R.id.issueNumber);
            binding = (TextView) itemView.findViewById(R.id.binding);
            format = (TextView) itemView.findViewById(R.id.format);
            pagesCount = (TextView) itemView.findViewById(R.id.pagesCount);
            originalName = (TextView) itemView.findViewById(R.id.originalName);
            price = (TextView) itemView.findViewById(R.id.price);
            notes = (TextView) itemView.findViewById(R.id.notes);
            authors = (TextView) itemView.findViewById(R.id.authors);
            series = (TextView) itemView.findViewById(R.id.series);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            URL = (TextView) itemView.findViewById(R.id.url);
        }
    }

    private Comics comics;

    public ComicsDetailRVAdapter(Comics comics) {
        this.comics = comics;
    }

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
            vh.notes.setText(Html.fromHtml(comics.getNotes()));
            vh.description.setText(Html.fromHtml(comics.getDescription()));
            vh.authors.setText(comics.getAuthors());
            vh.series.setText(comics.getSeries());
            vh.cover.setImageBitmap(comics.getCover());
            vh.URL.setText("http://comicsdb.cz/comics.php?id=" + comics.getId().toString());
            //TODO getString(R.string.url_comics_detail)
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
