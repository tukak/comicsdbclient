package cz.kutner.comicsdb.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.event.ComicsDetailResultEvent;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.adapter.ComicsDetailRVAdapter;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.task.FetchComicsDetailTask;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 31.3.2015.
 */


public class ComicsDetailFragment extends Fragment {

    private String LOG_TAG = getClass().getSimpleName();
    private ViewGroup container;

    public ComicsDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        this.container = container;
        if (!Utils.isConnected(this.getActivity())) {
            rootView = inflater.inflate(R.layout.loading_error, container, false);
            Button tryAgainButton = (Button) rootView.findViewById(R.id.try_again);
            tryAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isConnected(getActivity())) {
                        loadComicsDetail();
                    }
                }
            });
        } else {
            rootView = inflater.inflate(R.layout.loading, container, false);
            loadComicsDetail();
        }
        return rootView;
    }

    private void loadComicsDetail() {
        Bundle args = this.getArguments();
        Integer id = args.getInt("id");
        FetchComicsDetailTask task = new FetchComicsDetailTask(this.getActivity().getApplicationContext());
        task.execute(id);
        EventBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onAsyncTaskResult(ComicsDetailResultEvent event) {
        Comics result = event.getResult();
        Activity activity = getActivity();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.comics_detail, container, false);
        container.removeAllViews();
        container.addView(view);
        ComicsDetailRVAdapter adapter = new ComicsDetailRVAdapter(result, this.getActivity().getApplicationContext());
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.comments_detail_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
    }
}
