package cz.kutner.comicsdb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukas.Kutner on 24.3.2015.
 */
public class MainFragment extends Fragment {
    ArrayAdapter<Comics> mComicsAdapter;
    private final String LOG_TAG = MainFragment.class.getSimpleName();

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<Comics> comicsList = new ArrayList<Comics>();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                FetchComicsListTask task = new FetchComicsListTask();
                String searchText = Normalizer.normalize(query, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                task.execute("http://comicsdb.cz/search.php?searchfor=" + searchText);
                return true;
            }


        };

        final SearchView.OnCloseListener closeListener = new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.i(LOG_TAG, "Close");
                return true;
            }
        };

        SearchView searchView = (SearchView) rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setOnCloseListener(closeListener);

        //načteme nové
        FetchComicsListTask task = new FetchComicsListTask();
        task.execute("http://comicsdb.cz/comicslist.php");

        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mComicsAdapter =
                new ArrayAdapter<Comics>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_comics, // The name of the layout ID.
                        R.id.list_item_comics_textview, // The ID of the textview to populate.
                        comicsList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        view.setTag(this.getItem(position).getUrl());
                        return view;
                    }
                };

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(mComicsAdapter);
        return rootView;
    }

    public class FetchComicsListTask extends AsyncTask<String, Void, List<Comics>> {
        private final String LOG_TAG = FetchComicsListTask.class.getSimpleName();

        @Override
        protected void onPostExecute(List<Comics> result) {
            if (result != null) {
                mComicsAdapter.clear();
                mComicsAdapter.addAll(result);
            }
        }

        @Override
        protected List<Comics> doInBackground(String... params) {
            return Utils.getComicsListFromURL(params[0]);
        }
    }
}
