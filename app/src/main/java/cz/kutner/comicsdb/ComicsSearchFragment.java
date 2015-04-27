package cz.kutner.comicsdb;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ComicsSearchFragment extends Fragment {

    private final String LOG_TAG = ComicsDetailFragment.class.getSimpleName();
    List<Comics> comicsList = new ArrayList<Comics>();
    ArrayAdapter<Comics> mComicsAdapter;

    public ComicsSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_comics_search, container, false);
        Bundle args = this.getArguments();
        String searchText = args.getString("query");
        searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        FetchComicsListTask task = new FetchComicsListTask();
        task.execute("http://comicsdb.cz/search.php?searchfor=" + searchText);
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
        ListView listView = (ListView) rootView.findViewById(R.id.searchResultList);
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
