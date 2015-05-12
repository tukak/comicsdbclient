package cz.kutner.comicsdb;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 31.3.2015.
 */


public class ComicsDetailFragment extends Fragment {

    private String LOG_TAG = getClass().getSimpleName();
    private ViewGroup container;

    public ComicsDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        this.container = container;
        if (!Utils.isConnected(this.getActivity())) {
            rootView = inflater.inflate(R.layout.loading_error, container, false);
            Button tryAgainButton = (Button) rootView.findViewById(R.id.try_again);
            tryAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isConnected(getActivity())) {
                        loadComicsDetail();
                    }
                }
            });
        } else {
            rootView = inflater.inflate(R.layout.loading, container, false);
            loadComicsDetail();
        }
        return rootView;
    }

    private void loadComicsDetail() {
        Bundle args = this.getArguments();
        Integer id = args.getInt("id");
        FetchComicsDetailTask task = new FetchComicsDetailTask(this.getActivity().getApplicationContext());
        task.execute(id);
        EventBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onAsyncTaskResult(ComicsDetailResultEvent event) {
        Comics result = event.getResult();
        Activity activity = getActivity();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.comics_detail, container, false);
        container.removeAllViews();
        container.addView(view);
        TextView name = (TextView) activity.findViewById(R.id.name);
        TextView rating = (TextView) activity.findViewById(R.id.rating);
        TextView description = (TextView) activity.findViewById(R.id.description);
        TextView genre = (TextView) activity.findViewById(R.id.genre);
        TextView publisher = (TextView) activity.findViewById(R.id.publisher);
        TextView issueNumber = (TextView) activity.findViewById(R.id.issueNumber);
        TextView binding = (TextView) activity.findViewById(R.id.binding);
        TextView format = (TextView) activity.findViewById(R.id.format);
        TextView pagesCount = (TextView) activity.findViewById(R.id.pagesCount);
        TextView originalName = (TextView) activity.findViewById(R.id.originalName);
        TextView price = (TextView) activity.findViewById(R.id.price);
        TextView notes = (TextView) activity.findViewById(R.id.notes);
        TextView authors = (TextView) activity.findViewById(R.id.authors);
        TextView series = (TextView) activity.findViewById(R.id.series);
        ImageView cover = (ImageView) activity.findViewById(R.id.cover);
        TextView URL = (TextView) activity.findViewById(R.id.url);

        name.setText(result.getName());
        if (result.getRating() > 0) {
            rating.setText(result.getRating().toString() + "% (" + result.getVoteCount().toString() + ")");
        } else {
            rating.setText("< 5 hodnocení");
        }
        genre.setText(Utils.nvl(result.getGenre(), ""));
        publisher.setText(result.getPublisher() + " - " + result.getPublished());
        issueNumber.setText("Vydání: " + Utils.nvl(result.getIssueNumber(), "") + " tisk: " + Utils.nvl(result.getPrint(), ""));
        binding.setText("Vazba: " + Utils.nvl(result.getBinding(), ""));
        format.setText("Formát: " + Utils.nvl(result.getFormat(), ""));
        pagesCount.setText("Počet stran: " + Utils.nvl(result.getPagesCount(), ""));
        if (result.getOriginalName() != null) {
            originalName.setText("Původně: " + result.getOriginalName());
            if (result.getOriginalPublisher() != null) {
                originalName.setText(originalName.getText() + " - " + result.getOriginalPublisher());
            }
        } else {
            originalName.setText("");
        }
        price.setText("Cena: " + Utils.nvl(result.getPrice(), ""));
        notes.setText(result.getNotes());
        description.setText(result.getDescription());
        authors.setText(result.getAuthors());
        series.setText(result.getSeries());
        cover.setImageBitmap(result.getCover());
        URL.setText(getString(R.string.url_comics_detail) + result.getId().toString());

        CommentsRVAdapter adapter = new CommentsRVAdapter(result.getComments());
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.comments_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        //TODO nastavit minimální výšku
    }
}
