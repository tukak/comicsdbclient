package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.event.SeriesResultEvent;
import cz.kutner.comicsdb.holder.SeriesViewHolder;
import cz.kutner.comicsdb.model.Series;
import cz.kutner.comicsdb.task.FetchSeriesTask;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class SeriesFragment extends AbstractFragment<Series, SeriesResultEvent> {



    public SeriesFragment() {
        super();
        preloadCount = 20;
    }

    public static SeriesFragment newInstance() {

        Bundle args = new Bundle();

        SeriesFragment fragment = new SeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new EasyRecyclerAdapter<Series>(
                getContext(),
                SeriesViewHolder.class,
                data);
        return super.onCreateView(inflater, container, savedInstanceState);
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


