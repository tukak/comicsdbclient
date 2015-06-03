package cz.kutner.comicsdb.fragment;

import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.adapter.ForumRVAdapter;
import cz.kutner.comicsdb.event.ForumResultEvent;
import cz.kutner.comicsdb.model.ForumEntry;
import cz.kutner.comicsdb.task.FetchForumTask;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ForumFragment extends AbstractFragment<ForumEntry, ForumRVAdapter, ForumResultEvent> {

    public ForumFragment() {
        adapter = new ForumRVAdapter(data);
        fragment_view = R.layout.fragment_forum;
        recycler_view = R.id.forum_recycler_view;
        preloadCount = 20;
    }


    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchForumTask task = new FetchForumTask();
            //Bundle args = this.getArguments();
            //if (args != null && args.containsKey(SearchManager.QUERY)) { //neco vyhledavame
            //    String searchText = args.getString(SearchManager.QUERY);
            //    searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            //    task.execute(getString(R.string.url_comics_search) + searchText);
            //} else { //zobrazujeme nejnovější
            task.execute(getString(R.string.url_forum) + "?str=" + lastPage);
            //}
            lastPage++;
        }
    }

    @Subscribe
    public void onAsyncTaskResult(ForumResultEvent event) {
        super.onAsyncTaskResult(event);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Forum");
    }
}
