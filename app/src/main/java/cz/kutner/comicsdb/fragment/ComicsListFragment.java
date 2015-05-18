package cz.kutner.comicsdb.fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.squareup.otto.Subscribe;

import java.text.Normalizer;

import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.adapter.ComicsListRVAdapter;
import cz.kutner.comicsdb.task.FetchComicsListTask;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class ComicsListFragment extends Fragment {

    private final String LOG_TAG = getClass().getSimpleName();
    private ViewGroup container;
    private boolean searchRunning;

    public ComicsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        View rootView;
        if (!Utils.isConnected(this.getActivity())) {
            rootView = inflater.inflate(R.layout.loading_error, container, false);
            Button tryAgainButton = (Button) rootView.findViewById(R.id.try_again);
            tryAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isConnected(getActivity())) {
                        loadComics();
                    }
                }
            });
        } else {
            rootView = inflater.inflate(R.layout.loading, container, false);
            loadComics();
        }
        EventBus.getInstance().register(this);
        return rootView;
    }

    @Override
    public void onDestroy() {
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }

    private void loadComics() {
        if (searchRunning == false) {
            searchRunning = true;
            FetchComicsListTask task = new FetchComicsListTask();
            Bundle args = this.getArguments();
            if (args != null && args.containsKey(SearchManager.QUERY)) { //neco vyhledavame
                String searchText = args.getString(SearchManager.QUERY);
                searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                task.execute(getString(R.string.url_comics_search) + searchText);
            } else { //zobrazujeme nejnovější
                task.execute(getString(R.string.url_comics_list_new));
            }
        }
    }

    @Subscribe
    public void onAsyncTaskResult(ComicsSearchResultEvent event) {
        searchRunning = false;
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_comics_list, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.comics_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        ComicsListRVAdapter adapter = new ComicsListRVAdapter(event.getResult());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        container.removeAllViews();
        container.addView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        SearchView sw = (SearchView) this.getActivity().findViewById(R.id.toolbar).findViewById(R.id.searchView);
        sw.setQuery("", false);
        sw.setIconified(true);
        //TODO - pridat loadovaci kolecko
        loadComics();
    }
}


