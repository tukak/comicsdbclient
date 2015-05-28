package cz.kutner.comicsdb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import cz.kutner.comicsdb.fragment.ComicsDetailFragment;
import cz.kutner.comicsdbclient.comicsdbclient.R;

public class ComicsDetailActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Detail");
        Intent intent = getIntent();
        Integer id = intent.getIntExtra(MainActivity.COMICS_ID, 0);
        Fragment fragment = new ComicsDetailFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


}
