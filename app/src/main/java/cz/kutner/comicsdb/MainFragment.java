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
                task.execute(query);
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

        FetchNewComicsListTask task = new FetchNewComicsListTask();
        task.execute();

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
            Document doc;
            List<Comics> result = new ArrayList<Comics>();
            try {
                String searchText = Normalizer.normalize(params[0], Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                doc = Jsoup.connect("http://comicsdb.cz/search.php?searchfor=" + searchText).get();
                Element table = doc.select("table[summary=Přehled comicsů]").first();
                for (Element row : table.select("tbody tr")) {
                    Elements columns = row.select("td");
                /* 0 <th scope="col" title="Titul comicsu">TITUL</th>
		        1    <th scope="col" title="Rok vydání">ROK</th>
		        2    <th scope="col">ISBN/ISSN</th>
		        3    <th scope="col" title="Celkové hodnocení">%</th>
		        4    <th scope="col" title="Počet zobrazení">ZOB</th>
		        5    <th scope="col" title="Počet komentářů">KOM</th>
		        6    <th scope="col" title="Počet hodnocení">HOD</th>
		        7   <th scope="col" title="Datum vložení">VLOŽENO</th>
		        8    <th scope="col" title="Datum aktualizace">AKTUAL</th> */
                    String title = columns.get(0).select("a").first().text();
                    String url = columns.get(0).select("a").first().attr("href");
                    String year = columns.get(1).text();
                    String rating = columns.get(3).text();
                    if (rating.isEmpty()) {rating = "0";};
                    Comics comics = new Comics(title, url);
                    comics.setPublished(year);
                    comics.setRating(Integer.valueOf(rating));
                    result.add(comics);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            return result;
        }
    }

    public class FetchNewComicsListTask extends AsyncTask<String, Void, List<Comics>> {
        private final String LOG_TAG = FetchNewComicsListTask.class.getSimpleName();

        @Override
        protected void onPostExecute(List<Comics> result) {
            if (result != null) {
                mComicsAdapter.clear();
                mComicsAdapter.addAll(result);
            }
        }

        @Override
        protected List<Comics> doInBackground(String... params) {
            Document doc;
            List<Comics> result = new ArrayList<Comics>();
            try {
                doc = Jsoup.connect(
                        "http://comicsdb.cz/").get();
                Iterator<Element> new_comics = doc.select("#rightcolumn a")
                        .iterator();
                new_comics.next();  //prvni tri nechceme
                new_comics.next();
                new_comics.next();
                new_comics.next();
                while (new_comics.hasNext()) {
                    Element a = new_comics.next();
                    String title = a.text();
                    String url = a.attr("href");
                    Comics comics = new Comics(title, url);
                    result.add(comics);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            return result;
        }
    }
}
