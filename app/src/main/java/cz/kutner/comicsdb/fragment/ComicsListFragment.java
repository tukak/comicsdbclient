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
import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.task.FetchComicsListTask;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class ComicsListFragment extends AbstractFragment<Comics, ComicsListRVAdapter, ComicsSearchResultEvent> {


    @DebugLog
    public ComicsListFragment() {
        super();
        adapter = new ComicsListRVAdapter(data);
        preloadCount = 20;
    }

    public static ComicsListFragment newInstance() {

        Bundle args = new Bundle();

        ComicsListFragment fragment = new ComicsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @DebugLog
    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchComicsListTask task = new FetchComicsListTask();
            Bundle args = this.getArguments();
            if (args != null && args.containsKey(SearchManager.QUERY)) { //neco vyhledavame
                String searchText = args.getString(SearchManager.QUERY);
                searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                task.execute(FetchComicsListTask.SEARCH, searchText);
                endless = false;
            } else { //zobrazujeme nejnovější
                task.execute(FetchComicsListTask.LIST, String.valueOf(lastPage));
            }
            lastPage++;
        }
    }

    @Subscribe
    @DebugLog
    public void onAsyncTaskResult(ComicsSearchResultEvent event) {
        super.onAsyncTaskResult(event);
    }

    @Override
    @DebugLog
    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        Tracker tracker = ComicsDBApplication.getTracker();
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\"");
            tracker.setScreenName("ComicsListFragment - Search");
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Comicsy");
            tracker.setScreenName("ComicsListFragment - List");
        }
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}


