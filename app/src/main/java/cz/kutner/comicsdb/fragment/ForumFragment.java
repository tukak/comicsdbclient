package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.adapter.ForumRVAdapter;
import cz.kutner.comicsdb.event.ForumResultEvent;
import cz.kutner.comicsdb.model.ForumEntry;
import cz.kutner.comicsdb.task.FetchForumTask;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ForumFragment extends AbstractFragment<ForumEntry, ForumRVAdapter, ForumResultEvent> {

    public ForumFragment() {
        adapter = new ForumRVAdapter(data);
        preloadCount = 20;
        spinnerEnabled = true;
        spinnerValues = new String[]{"Všechna fora", "* Připomínky a návrhy", "Fabula Rasa", "Filmový klub", "Pindárna", "Povinná četba", "Poznej comics nebo postavu", "Sběratelský klub",
                "Slevy, výprodeje, bazary", "Srazy, cony, festivaly", "Stripy, jouky, fejky :)"};
    }

    public static ForumFragment newInstance() {

        Bundle args = new Bundle();

        ForumFragment fragment = new ForumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchForumTask task = new FetchForumTask();
            String searchText = "";
            //Bundle args = this.getArguments();
            //if (args != null && args.containsKey(SearchManager.QUERY)) { //neco vyhledavame
            //    String searchText = args.getString(SearchManager.QUERY);
            //    searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            //    task.execute(getString(R.string.url_comics_search) + searchText);
            //} else { //zobrazujeme nejnovější
            task.execute(String.valueOf(lastPage), filter, searchText); //Stránka, Kategorie, Hledaný text

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
