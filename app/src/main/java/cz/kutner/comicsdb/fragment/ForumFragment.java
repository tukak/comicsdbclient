package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.event.ForumResultEvent;
import cz.kutner.comicsdb.holder.ForumViewHolder;
import cz.kutner.comicsdb.model.ForumEntry;
import cz.kutner.comicsdb.task.FetchForumTask;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class ForumFragment extends AbstractFragment<ForumEntry, ForumResultEvent> {

    public ForumFragment() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new EasyRecyclerAdapter<ForumEntry>(
                getContext(),
                ForumViewHolder.class,
                data);
        return super.onCreateView(inflater, container, savedInstanceState);
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
        Tracker tracker = ComicsDBApplication.getTracker();
        tracker.setScreenName("ForumFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
