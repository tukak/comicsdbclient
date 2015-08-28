package cz.kutner.comicsdb.fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.Normalizer;

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
            Bundle args = this.getArguments();
            if (args != null && args.containsKey(SearchManager.QUERY)) { //neco vyhledavame
                String searchText = args.getString(SearchManager.QUERY);
                searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                Observable.just(searchText)
                        .subscribeOn(Schedulers.io())
                        .map(s -> SeriesConnector.search(s))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(series -> {
                            result = series;
                            showData();
                        });
                endless = false;
            } else { //zobrazujeme nejnovější
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
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        Tracker tracker = ComicsDBApplication.getTracker();
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\"");
            tracker.setScreenName("SeriesFragment - Search");
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
            tracker.send(new HitBuilders.EventBuilder().setCategory("Search").setAction(args.getString(SearchManager.QUERY)).build());
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Serie");
            tracker.setScreenName("SeriesFragment - List");
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }
}


