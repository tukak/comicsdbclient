package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.ForumConnector;
import cz.kutner.comicsdb.holder.ForumViewHolder;
import cz.kutner.comicsdb.model.ForumEntry;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class ForumFragment extends AbstractFragment<ForumEntry> {

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
            String searchText = "";
            Observable.just(lastPage)
                    .observeOn(Schedulers.io())
                    .map(integer -> ForumConnector.getFiltered(integer, filter, searchText))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(forumEntries -> {
                        result = forumEntries;
                        showData();
                    });
            lastPage++;
        }
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
