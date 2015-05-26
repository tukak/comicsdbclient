package cz.kutner.comicsdb.fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.squareup.otto.Subscribe;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.adapter.ComicsListRVAdapter;
import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.task.FetchComicsListTask;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class ComicsListFragment extends AbstractFragment {

    private boolean searchRunning;
    private boolean endless;
    List<Comics> data = new ArrayList<>();
    ComicsListRVAdapter adapter = new ComicsListRVAdapter(data);
    LinearLayoutManager llm;
    boolean loading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    void loadData() {
        if (searchRunning == false) {
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
                endless = true;
            }
            lastPage++;
        }
    }

    @Subscribe
    public void onAsyncTaskResult(ComicsSearchResultEvent event) {
        searchRunning = false;
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        if (firstLoad) {
            View view = inflater.inflate(R.layout.fragment_comics_list, container, false);
            RecyclerView rv = (RecyclerView) view.findViewById(R.id.comics_recycler_view);
            llm = new LinearLayoutManager(view.getContext());
            rv.setLayoutManager(llm);
            rv.setAdapter(adapter);
            if (endless) {
                rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        visibleItemCount = llm.getChildCount();
                        totalItemCount = llm.getItemCount();
                        pastVisiblesItems = llm.findFirstVisibleItemPosition();
                        if (!loading) {
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 20) {
                                loading = true;
                                loadData();
                            }
                        }
                    }
                });
            }
            container.removeAllViews();
            container.addView(view);
            firstLoad = false;
        }
        if (!endless) {
            data.clear();
        }
        data.addAll(event.getResult());
        adapter.notifyDataSetChanged();
        loading = false;
    }
}


