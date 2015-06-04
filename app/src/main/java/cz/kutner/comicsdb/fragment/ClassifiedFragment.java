package cz.kutner.comicsdb.fragment;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Spinner;

import com.squareup.otto.Subscribe;

import cz.kutner.comicsdb.adapter.ClassifiedRVAdapter;
import cz.kutner.comicsdb.event.ClassifiedResultEvent;
import cz.kutner.comicsdb.model.Classified;
import cz.kutner.comicsdb.task.FetchClassifiedTask;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ClassifiedFragment extends AbstractFragment<Classified, ClassifiedRVAdapter, ClassifiedResultEvent> {


    public ClassifiedFragment() {
        adapter = new ClassifiedRVAdapter(data);
        preloadCount = 8;
        spinnerEnabled = true;
        spinnerValues = new String[]{"Všechny inzeráty", "Prodám", "Koupím", "Vyměním", "Ostatní"};
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
    }
}
