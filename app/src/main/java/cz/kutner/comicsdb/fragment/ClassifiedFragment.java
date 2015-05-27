package cz.kutner.comicsdb.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.adapter.ClassifiedRVAdapter;
import cz.kutner.comicsdb.adapter.ForumRVAdapter;
import cz.kutner.comicsdb.event.ClassifiedResultEvent;
import cz.kutner.comicsdb.event.ForumResultEvent;
import cz.kutner.comicsdb.model.Classified;
import cz.kutner.comicsdb.task.FetchClassifiedTask;
import cz.kutner.comicsdb.task.FetchForumTask;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ClassifiedFragment extends AbstractFragment<Classified, ClassifiedRVAdapter, ClassifiedResultEvent> {


    public ClassifiedFragment() {
        adapter = new ClassifiedRVAdapter(data);
        fragment_view = R.layout.fragment_classified;
        recycler_view = R.id.classified_recycler_view;
        preloadCount = 8;
    }

    void loadData() {
        if (searchRunning == false) {
            searchRunning = true;
            FetchClassifiedTask task = new FetchClassifiedTask(this.getActivity().getApplicationContext());
            //Bundle args = this.getArguments();
            //if (args != null && args.containsKey(SearchManager.QUERY)) { //neco vyhledavame
            //    String searchText = args.getString(SearchManager.QUERY);
            //    searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            //    task.execute(getString(R.string.url_comics_search) + searchText);
            //} else { //zobrazujeme nejnovější
            task.execute(getString(R.string.url_classified) + "?str=" + lastPage);
            //}
            lastPage++;
        }
    }

    @Subscribe
    public void onAsyncTaskResult(ClassifiedResultEvent event) {
        super.onAsyncTaskResult(event);
    }
}
