package cz.kutner.comicsdb.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.adapter.ComicsDetailRVAdapter;
import cz.kutner.comicsdb.event.ComicsDetailResultEvent;
import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.task.FetchComicsDetailTask;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 31.3.2015.
 */


public class ComicsDetailFragment extends AbstractFragment<Comics> {

    void loadData() {
        Bundle args = this.getArguments();
        Integer id = args.getInt("id");
        FetchComicsDetailTask task = new FetchComicsDetailTask(this.getActivity().getApplicationContext());
        task.execute(id);
    }

    @Subscribe
    public void onAsyncTaskResult(ComicsDetailResultEvent event) {
        Comics result = event.getResult();
        Activity activity = getActivity();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_comics_detail, container, false);
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
