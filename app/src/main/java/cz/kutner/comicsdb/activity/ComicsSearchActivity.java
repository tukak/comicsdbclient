package cz.kutner.comicsdb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.fragment.ComicsListFragment;

public class ComicsSearchActivity extends AbstractActivity {

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
            Fragment fragment = ComicsListFragment.newInstance();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}
