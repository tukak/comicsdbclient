package cz.kutner.comicsdb.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.connector.helper.ClassifiedHelper;
import cz.kutner.comicsdb.holder.ClassifiedViewHolder;
import cz.kutner.comicsdb.model.Classified;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class ClassifiedFragment extends AbstractFragment<Classified> {


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
            String searchText = "";
            ComicsDBApplication.getClassifiedService().filteredClassifiedList(lastPage, ClassifiedHelper.getCategoryId(filter), searchText)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(classifieds -> {
                        result = classifieds;
                        showData();
                    });
            lastPage++;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bazar");
        Tracker tracker = ComicsDBApplication.getTracker();
        tracker.setScreenName("ClassifiedFragment");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Zobrazení inzerátů")
                .putContentType("Inzerát"));
    }


}
