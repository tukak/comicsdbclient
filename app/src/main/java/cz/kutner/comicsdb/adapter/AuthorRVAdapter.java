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
import cz.kutner.comicsdb.model.Author;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class AuthorRVAdapter extends RecyclerView.Adapter<AuthorRVAdapter.AuthorViewHolder> {

    public static class AuthorViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = getClass().getSimpleName();

        @Bind(R.id.authorName)
        TextView authorName;
        @Bind(R.id.authorCountry)
        TextView authorCountry;

        AuthorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private List<Author> authors;

    public AuthorRVAdapter(List<Author> authors) {
        this.authors = authors;
    }



    @Override
    public int getItemCount() {
        return authors.size();
    }

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_authors, viewGroup, false);
        return new AuthorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder seriesViewHolder, int i) {
        seriesViewHolder.authorName.setText(authors.get(i).getName());
        seriesViewHolder.authorCountry.setText(authors.get(i).getCountry());
    }

}
