package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.adapter.SeriesRVAdapter;
import cz.kutner.comicsdb.event.SeriesResultEvent;
import cz.kutner.comicsdb.model.Series;
import cz.kutner.comicsdb.task.FetchSeriesTask;

public class SeriesFragment extends AbstractFragment<Series, SeriesRVAdapter, SeriesResultEvent> {



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


    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchSeriesTask task = new FetchSeriesTask();
            task.execute(FetchSeriesTask.LIST, String.valueOf(lastPage));
            lastPage++;
        }
    }

    @Subscribe

    public void onAsyncTaskResult(SeriesResultEvent event) {
        super.onAsyncTaskResult(event);
    }

    @Override

    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        Tracker tracker = ComicsDBApplication.getTracker();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Serie");
        tracker.setScreenName("SeriesFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}


