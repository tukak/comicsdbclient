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

public class NewsFragment extends AbstractFragment<NewsItem, NewsRVAdapter, NewsResultEvent> {



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


    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchNewsTask task = new FetchNewsTask();
            task.execute();
            lastPage++;
        }
    }

    @Subscribe

    public void onAsyncTaskResult(NewsResultEvent event) {
        super.onAsyncTaskResult(event);
    }

    @Override

    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        Tracker tracker = ComicsDBApplication.getTracker();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Novinky");
        tracker.setScreenName("NewsFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}


