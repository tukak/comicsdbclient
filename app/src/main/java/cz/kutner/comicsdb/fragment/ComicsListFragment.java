package cz.kutner.comicsdb.fragment;

import android.app.SearchManager;
import android.os.Bundle;

import com.squareup.otto.Subscribe;

import java.text.Normalizer;

import cz.kutner.comicsdb.adapter.ComicsListRVAdapter;
import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.task.FetchComicsListTask;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class ComicsListFragment extends AbstractFragment<Comics, ComicsListRVAdapter, ComicsSearchResultEvent> {


    public ComicsListFragment() {
        adapter = new ComicsListRVAdapter(data);
        fragment_view = R.layout.fragment_comics_list;
        recycler_view = R.id.comics_recycler_view;
        preloadCount = 20;
    }

    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchComicsListTask task = new FetchComicsListTask();
            Bundle args = this.getArguments();
            if (args != null && args.containsKey(SearchManager.QUERY)) { //neco vyhledavame
                String searchText = args.getString(SearchManager.QUERY);
                searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                task.execute(getString(R.string.url_comics_search) + searchText);
                endless = false;
            } else { //zobrazujeme nejnovější
                task.execute(getString(R.string.url_comics_list_new) + "?str=" + lastPage);
            }
            lastPage++;
        }
    }

    @Subscribe
    public void onAsyncTaskResult(ComicsSearchResultEvent event) {
        super.onAsyncTaskResult(event);
    }
}


