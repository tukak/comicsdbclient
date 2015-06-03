package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.event.AbstractResultEvent;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public abstract class AbstractFragment<Item, Adapter extends RecyclerView.Adapter, Event extends AbstractResultEvent> extends Fragment {
    String LOG_TAG = getClass().getSimpleName();
    ViewGroup container;
    int lastPage;
    boolean firstLoad;
    boolean searchRunning;
    boolean loading;
    Item lastItem;
    List<Item> data = new ArrayList<>();
    List<Item> result = new ArrayList<>();
    int pastVisibleItems, visibleItemCount, totalItemCount;
    LinearLayoutManager llm;
    Adapter adapter;
    int fragment_view;
    int recycler_view;
    int preloadCount;
    boolean endless;

    public AbstractFragment() {
        EventBus.getInstance().register(this);
        lastPage = 1;
        loading = false;
        endless = true;
        fragment_view = R.layout.fragment;
        recycler_view = R.id.recycler_view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;
        return null;
    }

    abstract void loadData();

    @Override
    public void onDestroy() {
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        View view;
        SearchView sw = (SearchView) this.getActivity().findViewById(R.id.toolbar).findViewById(R.id.searchView);
        sw.setQuery("", false);
        sw.setIconified(true);
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        if (!Utils.isConnected(this.getActivity())) {
            view = inflater.inflate(R.layout.loading_error, container, false);
            container.removeAllViews();
            container.addView(view);
            Button tryAgainButton = (Button) view.findViewById(R.id.try_again);
            tryAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isConnected(getActivity())) {
                        onStart();
                    }
                }
            });
        } else {
            view = inflater.inflate(R.layout.loading, container, false);
            container.removeAllViews();
            container.addView(view);
            firstLoad = true;
            loadData();
        }
    }

    public void onAsyncTaskResult(Event event) {
        searchRunning = false;
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        if (firstLoad) {
            View view = inflater.inflate(fragment_view, container, false);
            RecyclerView rv = (RecyclerView) view.findViewById(recycler_view);
            llm = new LinearLayoutManager(view.getContext());
            rv.setLayoutManager(llm);
            rv.setAdapter(adapter);
            if (endless) {
                rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        visibleItemCount = llm.getChildCount();
                        totalItemCount = llm.getItemCount();
                        pastVisibleItems = llm.findFirstVisibleItemPosition();
                        if (!loading) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount - preloadCount) {
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
        result = event.getResult();
        if (result.size() > 0) {
            if (lastItem == null || !(lastItem.equals(result.get(0)))) {
                lastItem = result.get(0);
                if (!endless) {
                    data.clear();
                }
                data.addAll(event.getResult());
                adapter.notifyDataSetChanged();
                loading = false;
            }
        }
    }
}