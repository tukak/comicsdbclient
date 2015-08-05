package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.adapter.NewsRVAdapter;
import cz.kutner.comicsdb.event.NewsResultEvent;
import cz.kutner.comicsdb.model.NewsItem;
import cz.kutner.comicsdb.task.FetchNewsTask;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class NewsFragment extends AbstractFragment<NewsItem, NewsRVAdapter, NewsResultEvent> {


    @DebugLog
    public NewsFragment() {
        super();
        adapter = new NewsRVAdapter(data);
        preloadCount = 0;
        endless = false;
    }

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @DebugLog
    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchNewsTask task = new FetchNewsTask();
            task.execute();
            lastPage++;
        }
    }

    @Subscribe
    @DebugLog
    public void onAsyncTaskResult(NewsResultEvent event) {
        super.onAsyncTaskResult(event);
    }

    @Override
    @DebugLog
    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        Tracker tracker = ComicsDBApplication.getTracker();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Novinky");
        tracker.setScreenName("NewsFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}


