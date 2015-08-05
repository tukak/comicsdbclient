package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.adapter.AuthorRVAdapter;
import cz.kutner.comicsdb.adapter.SeriesRVAdapter;
import cz.kutner.comicsdb.event.AuthorResultEvent;
import cz.kutner.comicsdb.event.SeriesResultEvent;
import cz.kutner.comicsdb.model.Author;
import cz.kutner.comicsdb.model.Series;
import cz.kutner.comicsdb.task.FetchAuthorTask;
import cz.kutner.comicsdb.task.FetchComicsListTask;
import cz.kutner.comicsdb.task.FetchSeriesTask;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class AuthorFragment extends AbstractFragment<Author, AuthorRVAdapter, AuthorResultEvent> {


    @DebugLog
    public AuthorFragment() {
        super();
        adapter = new AuthorRVAdapter(data);
        preloadCount = 20;
    }

    public static AuthorFragment newInstance() {

        Bundle args = new Bundle();

        AuthorFragment fragment = new AuthorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @DebugLog
    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchAuthorTask task = new FetchAuthorTask();
            task.execute(FetchAuthorTask.LIST, String.valueOf(lastPage));
            lastPage++;
        }
    }

    @Subscribe
    @DebugLog
    public void onAsyncTaskResult(AuthorResultEvent event) {
        super.onAsyncTaskResult(event);
    }

    @Override
    @DebugLog
    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        Tracker tracker = ComicsDBApplication.getTracker();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Autoři");
        tracker.setScreenName("AuthorFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}


