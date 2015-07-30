package cz.kutner.comicsdb.fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;

import java.text.Normalizer;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.adapter.ComicsListRVAdapter;
import cz.kutner.comicsdb.adapter.SeriesRVAdapter;
import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.event.SeriesResultEvent;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.model.Series;
import cz.kutner.comicsdb.task.FetchComicsListTask;
import cz.kutner.comicsdb.task.FetchSeriesTask;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class SeriesFragment extends AbstractFragment<Series, SeriesRVAdapter, SeriesResultEvent> {


    @DebugLog
    public SeriesFragment() {
        super();
        adapter = new SeriesRVAdapter(data);
        preloadCount = 20;
    }

    public static SeriesFragment newInstance() {

        Bundle args = new Bundle();

        SeriesFragment fragment = new SeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @DebugLog
    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchSeriesTask task = new FetchSeriesTask();
            task.execute(FetchComicsListTask.LIST, String.valueOf(lastPage));
            lastPage++;
        }
    }

    @Subscribe
    @DebugLog
    public void onAsyncTaskResult(SeriesResultEvent event) {
        super.onAsyncTaskResult(event);
    }

    @Override
    @DebugLog
    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        Tracker tracker = ComicsDBApplication.getTracker();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Serie");
        tracker.setScreenName("SeriesFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}


