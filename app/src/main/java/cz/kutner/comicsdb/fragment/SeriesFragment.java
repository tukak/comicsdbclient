package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.SeriesConnector;
import cz.kutner.comicsdb.holder.SeriesViewHolder;
import cz.kutner.comicsdb.model.Series;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class SeriesFragment extends AbstractFragment<Series> {


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
            Observable.just(lastPage)
                    .subscribeOn(Schedulers.io())
                    .map(integer -> SeriesConnector.get(integer))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(series -> {
                        result = series;
                        showData();
                    });
            lastPage++;
        }
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


