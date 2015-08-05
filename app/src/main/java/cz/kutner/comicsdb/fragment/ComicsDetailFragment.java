package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.adapter.ComicsDetailRVAdapter;
import cz.kutner.comicsdb.event.ComicsDetailResultEvent;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.task.FetchComicsDetailTask;
import hugo.weaving.DebugLog;
import pl.aprilapps.switcher.Switcher;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 31.3.2015.
 */


public class ComicsDetailFragment extends Fragment {

    private Switcher switcher;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    @Bind(R.id.progress_view)
    LinearLayout progressView;
    @Bind(R.id.try_again)
    Button tryAgain;
    @Bind(R.id.error_view)
    LinearLayout errorView;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.content)
    LinearLayout content;

    @Bind(R.id.filter_text)
    TextView filterText;

    private Comics comics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    @DebugLog
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);
        switcher = new Switcher.Builder()
                .withContentView(content)
                .withEmptyView(emptyView)
                .withProgressView(progressView)
                .withErrorView(errorView)
                .build();
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(llm);
        spinner.setVisibility(View.GONE);
        filterText.setVisibility(View.GONE);
        switcher.showProgressView();
        return view;

    }

    @DebugLog
    private void loadData() {
        Bundle args = this.getArguments();
        Integer id = args.getInt("id");
        FetchComicsDetailTask task = new FetchComicsDetailTask();
        task.execute(id);
    }

    @Override
    public void onPause() {
        super.onPause();
        ComicsDBApplication.getEventBus().unregister(this);
    }


    @OnClick(R.id.try_again)
    public void tryAgainButtonClicked() {
        if (Utils.isConnected()) {
            onResume();
        }
    }

    @Override
    @DebugLog
    public void onResume() {
        super.onResume();
        if (!Utils.isConnected()) {
            switcher.showErrorView();
        } else {
            switcher.showProgressView();
            ComicsDBApplication.getEventBus().register(this);
            if (comics != null) {
                showData();
            } else {
                loadData();
            }
        }
    }

    public static ComicsDetailFragment newInstance() {

        Bundle args = new Bundle();

        ComicsDetailFragment fragment = new ComicsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @DebugLog
    @Subscribe
    public void onAsyncTaskResult(ComicsDetailResultEvent event) {
        comics = event.getResult().get(0);
        showData();
    }

    @DebugLog
    private void showData() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(comics.getName());
        ComicsDetailRVAdapter adapter = new ComicsDetailRVAdapter(comics, ComicsDBApplication.getContext());
        recyclerView.setAdapter(adapter);
        adapter.setComics(comics);
        recyclerView.setHasFixedSize(true);
        switcher.showContentView();
        Tracker tracker = ComicsDBApplication.getTracker();
        tracker.setScreenName("ComicsDetailFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        tracker.send(new HitBuilders.EventBuilder().setCategory("Detail").setAction(comics.getName()).build());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
