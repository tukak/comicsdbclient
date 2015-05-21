package cz.kutner.comicsdb.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import cz.kutner.comicsdb.fragment.ComicsListFragment;
import cz.kutner.comicsdbclient.comicsdbclient.R;

public class ComicsSearchActivity extends AbstractActivity {
    private final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Fragment fragment = new ComicsListFragment();
            fragment.setArguments(getIntent().getExtras());
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Výsledek pro \"" + intent.getStringExtra(SearchManager.QUERY) + "\"");
            setSupportActionBar(toolbar);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}
