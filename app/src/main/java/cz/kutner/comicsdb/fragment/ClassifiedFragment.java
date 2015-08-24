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
import cz.kutner.comicsdb.event.ClassifiedResultEvent;
import cz.kutner.comicsdb.holder.ClassifiedViewHolder;
import cz.kutner.comicsdb.model.Classified;
import cz.kutner.comicsdb.task.FetchClassifiedTask;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class ClassifiedFragment extends AbstractFragment<Classified, ClassifiedResultEvent> {


    public ClassifiedFragment() {
        preloadCount = 8;
        spinnerEnabled = true;
        spinnerValues = new String[]{"Všechny inzeráty", "Prodám", "Koupím", "Vyměním", "Ostatní"};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new EasyRecyclerAdapter<Classified>(
                getContext(),
                ClassifiedViewHolder.class,
                data);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static ClassifiedFragment newInstance() {

        Bundle args = new Bundle();

        ClassifiedFragment fragment = new ClassifiedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            FetchClassifiedTask task = new FetchClassifiedTask();
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
    public void onAsyncTaskResult(ClassifiedResultEvent event) {
        super.onAsyncTaskResult(event);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bazar");
        Tracker tracker = ComicsDBApplication.getTracker();
        tracker.setScreenName("ClassifiedFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


}
