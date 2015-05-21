package cz.kutner.comicsdb.activity;

import android.os.Bundle;

import cz.kutner.comicsdb.fragment.ComicsListFragment;
import cz.kutner.comicsdbclient.comicsdbclient.R;

public class MainActivity extends AbstractActivity {
    public final static String COMICS_ID = "cz.kutner.comicsdbclient.comicsdbclient.comics_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ComicsListFragment())
                    .commit();
        }
    }
}
