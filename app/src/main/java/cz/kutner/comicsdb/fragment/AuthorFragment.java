package cz.kutner.comicsdb.fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.Normalizer;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.holder.AuthorViewHolder;
import cz.kutner.comicsdb.model.Author;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class AuthorFragment extends AbstractFragment<Author> {

    public AuthorFragment() {
        super();
        preloadCount = 20;
    }

    public static AuthorFragment newInstance() {

        Bundle args = new Bundle();

        AuthorFragment fragment = new AuthorFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new EasyRecyclerAdapter<Author>(
                getContext(),
                AuthorViewHolder.class,
                data);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    void loadData() {
        if (!searchRunning) {
            searchRunning = true;
            Bundle args = this.getArguments();
            if (args != null && args.containsKey(SearchManager.QUERY)) { //neco vyhledavame
                String searchText = args.getString(SearchManager.QUERY);
                searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                subscription = ComicsDBApplication.getAuthorService().authorSearch(searchText)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(authors -> {
                            result = authors;
                            showData();
                        });
                endless = false;
            } else { //zobrazujeme nejnovější
                subscription = ComicsDBApplication.getAuthorService().listAuthors(lastPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(authors -> {
                            result = authors;
                            showData();
                        });
                lastPage++;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = this.getArguments();
        Tracker tracker = ComicsDBApplication.getTracker();
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\"");
            tracker.setScreenName("AuthorFragment - Search");
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Autoři");
            tracker.setScreenName("AuthorFragment - List");
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
            Answers.getInstance().logContentView(new ContentViewEvent()
                    .putContentName("Zobrazení seznamu autorů")
                    .putContentType("Autor"));
        }
    }
}


