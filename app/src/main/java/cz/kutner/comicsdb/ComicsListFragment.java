package cz.kutner.comicsdb;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 27.4.2015.
 */
public class ComicsListFragment extends Fragment {

    private final String LOG_TAG = ComicsDetailFragment.class.getSimpleName();
    private List<Comics> comicsList = new ArrayList<>();
    private ComicsListRVAdapter adapter = new ComicsListRVAdapter(comicsList);

    public ComicsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = new View(getActivity());
        FetchComicsListTask task = new FetchComicsListTask(this.getActivity(), R.layout.fragment_comics_list, container);
        Bundle args = this.getArguments();
        if (args != null && args.containsKey("query")) { //neco vyhledavame
            String searchText = args.getString("query");
            searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            task.execute(getString(R.string.url_comics_search) + searchText);
        } else { //zobrazujeme nejnovější
            task.execute(getString(R.string.url_comics_list_new));
        }
        return rootView;
    }

    private class FetchComicsListTask extends FetchLoadingAsyncTask<String, Void, List<Comics>> {
        private final String LOG_TAG = FetchComicsListTask.class.getSimpleName();

        public FetchComicsListTask(Activity activity, Integer successLayout, ViewGroup container) {
            super(activity, successLayout, container);
        }

        @Override
        protected void onPostExecute(List<Comics> result) {
            if (result != null) {
                super.onPostExecute(result);
                RecyclerView rv = (RecyclerView) container.findViewById(R.id.comics_recycler_view);
                LinearLayoutManager llm = new LinearLayoutManager(container.getContext());
                rv.setLayoutManager(llm);
                rv.setAdapter(adapter);
                adapter.setComicsList(result);
                adapter.notifyDataSetChanged();
            }
        }


        @Override
        protected List<Comics> doInBackground(String... params) {
            return Utils.getComicsListFromURL(params[0]);
        }
    }
}


