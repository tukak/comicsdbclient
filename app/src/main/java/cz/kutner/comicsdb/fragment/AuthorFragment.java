package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.adapter.AuthorRVAdapter;
import cz.kutner.comicsdb.event.AuthorResultEvent;
import cz.kutner.comicsdb.model.Author;
import cz.kutner.comicsdb.task.FetchAuthorTask;

public class AuthorFragment extends AbstractFragment<Author, AuthorRVAdapter, AuthorResultEvent> {



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


    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchAuthorTask task = new FetchAuthorTask();
            task.execute(FetchAuthorTask.LIST, String.valueOf(lastPage));
            lastPage++;
        }
    }

    @Subscribe

    public void onAsyncTaskResult(AuthorResultEvent event) {
        super.onAsyncTaskResult(event);
    }

    @Override

    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        Tracker tracker = ComicsDBApplication.getTracker();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Autoři");
        tracker.setScreenName("AuthorFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}


