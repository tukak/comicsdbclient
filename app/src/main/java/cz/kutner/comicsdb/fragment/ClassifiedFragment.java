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
public class ClassifiedFragment extends AbstractFragment {

    private boolean searchRunning;
    List<Classified> data = new ArrayList<>();
    ClassifiedRVAdapter adapter = new ClassifiedRVAdapter(data);
    LinearLayoutManager llm;
    boolean loading = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    Classified lastItem;

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
        searchRunning = false;
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        if (firstLoad) {
            View view = inflater.inflate(R.layout.fragment_classified, container, false);
            RecyclerView rv = (RecyclerView) view.findViewById(R.id.classified_recycler_view);
            llm = new LinearLayoutManager(view.getContext());
            rv.setLayoutManager(llm);
            rv.setAdapter(adapter);
            rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisiblesItems = llm.findFirstVisibleItemPosition();
                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 8) {
                            loading = true;
                            loadData();
                        }
                    }
                }
            });

            container.removeAllViews();
            container.addView(view);
            firstLoad = false;
        }
        if (lastItem == null || !(lastItem.equals(event.getResult().get(1)))) {
            lastItem = event.getResult().get(1);
            data.addAll(event.getResult());
            adapter.notifyDataSetChanged();
            loading = false;
        }
    }
}
