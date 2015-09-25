package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.holder.NewsViewHolder;
import cz.kutner.comicsdb.model.NewsItem;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class NewsFragment extends AbstractFragment<NewsItem> {


    public NewsFragment() {
        super();
        preloadCount = 0;
        endless = false;
    }

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new EasyRecyclerAdapter<NewsItem>(
                getContext(),
                NewsViewHolder.class,
                data);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            ComicsDBApplication.getNewsService().listNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(news -> {
                        result = news;
                        showData();
                    });
            lastPage++;
        }
    }

    @Override

    public void onStart() {
        super.onStart();
        Tracker tracker = ComicsDBApplication.getTracker();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Novinky");
        tracker.setScreenName("NewsFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Zobrazení novinek")
                .putContentType("Novinky"));
    }
}


