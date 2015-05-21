package cz.kutner.comicsdb.fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.squareup.otto.Subscribe;

import java.text.Normalizer;

import cz.kutner.comicsdb.adapter.ComicsListRVAdapter;
import cz.kutner.comicsdb.adapter.ForumRVAdapter;
import cz.kutner.comicsdb.event.ComicsSearchResultEvent;
import cz.kutner.comicsdb.event.ForumResultEvent;
import cz.kutner.comicsdb.task.FetchComicsListTask;
import cz.kutner.comicsdb.task.FetchForumTask;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ForumFragment extends AbstractFragment {

    private boolean searchRunning;

    void loadData() {
        if (searchRunning == false) {
            searchRunning = true;
            FetchForumTask task = new FetchForumTask(this.getActivity().getApplicationContext());
            //Bundle args = this.getArguments();
            //if (args != null && args.containsKey(SearchManager.QUERY)) { //neco vyhledavame
            //    String searchText = args.getString(SearchManager.QUERY);
            //    searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            //    task.execute(getString(R.string.url_comics_search) + searchText);
            //} else { //zobrazujeme nejnovější
            task.execute(getString(R.string.url_forum));
            //}
        }
    }

    @Subscribe
    public void onAsyncTaskResult(ForumResultEvent event) {
        searchRunning = false;
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.forum_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        ForumRVAdapter adapter = new ForumRVAdapter(event.getResult());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        container.removeAllViews();
        container.addView(view);
    }
}
