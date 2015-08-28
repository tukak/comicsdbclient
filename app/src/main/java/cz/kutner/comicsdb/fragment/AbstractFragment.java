package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.Utils;
import pl.aprilapps.switcher.Switcher;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public abstract class AbstractFragment<Item> extends Fragment {
    int lastPage;
    private boolean firstLoad;
    boolean searchRunning;
    private boolean loading;
    private Item lastItem;
    List<Item> data = new ArrayList<>();
    List<Item> result = new ArrayList<>();
    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;
    EasyRecyclerAdapter adapter;
    int preloadCount;
    boolean endless;
    boolean spinnerEnabled;
    String[] spinnerValues;
    String filter;
    private Integer spinnerPosition;
    private Switcher switcher;
    @Bind(R.id.empty_view)
    LinearLayout emptyView;
    @Bind(R.id.progress_view)
    LinearLayout progressView;
    @Bind(R.id.error_view)
    LinearLayout errorView;
    @Bind(R.id.content)
    LinearLayout content;

    @Bind(R.id.recycler_view)
    RecyclerView rv;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.filter_text)
    TextView filterText;


    public AbstractFragment() {
        lastPage = 1;
        loading = false;
        endless = true;
        spinnerEnabled = false;
        filter = "";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        ButterKnife.bind(this, view);
        switcher = new Switcher.Builder()
                .withContentView(content)
                .withEmptyView(emptyView)
                .withProgressView(progressView)
                .withErrorView(errorView)
                .build();
        final LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        if (endless) {
            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    visibleItemCount = llm.getChildCount();
                    totalItemCount = llm.getItemCount();
                    pastVisibleItems = llm.findFirstVisibleItemPosition();
                    if (!loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount - preloadCount) {
                            loading = true;
                            loadData();
                        }
                    }
                }
            });
        }
        switcher.showProgressView();
        return view;
    }


    abstract void loadData();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkConnectionAndLoadData();
    }

    @OnClick(R.id.try_again)
    public void tryAgainButtonClicked() {
        if (Utils.isConnected()) {
            checkConnectionAndLoadData();
        }
    }

    private void checkConnectionAndLoadData() {
        if (!Utils.isConnected()) {
            switcher.showErrorView();
        } else {
            switcher.showProgressView();
            firstLoad = true;
            if (!result.isEmpty()) {
                showData();
            } else {
                loadData();
            }

        }
    }

    public void showData() {
        searchRunning = false;
        if (firstLoad) {
            if (!spinnerEnabled) {
                spinner.setVisibility(View.GONE);
                filterText.setVisibility(View.GONE);
            }
            if (spinnerEnabled) {
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, spinnerValues);
                spinner.setAdapter(spinnerAdapter);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                if (spinnerPosition != null) {
                    spinner.setSelection(spinnerPosition);
                } else {
                    spinnerPosition = 0;
                }
                spinner.setOnItemSelectedListener(new itemSelectedListener());
            }
            switcher.showContentView();
            firstLoad = false;
        }
        if (result.size() > 0) {
            if (lastItem == null || !(lastItem.equals(result.get(0)))) {
                lastItem = result.get(0);
                if (!endless) {
                    data.clear();
                }
                data.addAll(result);
            }
        }
        adapter.notifyDataSetChanged();
        loading = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class itemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (spinnerPosition != pos) {
                filter = spinner.getSelectedItem().toString();
                data.clear();
                lastItem = null;
                switcher.showProgressView();
                firstLoad = true;
                lastPage = 1;
                spinnerPosition = pos;
                loadData();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}