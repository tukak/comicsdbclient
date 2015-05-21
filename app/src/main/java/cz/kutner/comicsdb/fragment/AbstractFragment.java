package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public abstract class AbstractFragment extends Fragment {
    String LOG_TAG = getClass().getSimpleName();
    ViewGroup container;

    public AbstractFragment() {
        EventBus.getInstance().register(this);
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
                        loadData();
                    }
                }
            });
        } else {
            rootView = inflater.inflate(R.layout.loading, container, false);
            loadData();
        }
        return rootView;
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
        SearchView sw = (SearchView) this.getActivity().findViewById(R.id.toolbar).findViewById(R.id.searchView);
        sw.setQuery("", false);
        sw.setIconified(true);
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.loading, container, false);
        container.removeAllViews();
        container.addView(view);
        loadData();
    }
}
