package cz.kutner.comicsdb.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import cz.kutner.comicsdb.fragment.ClassifiedFragment;
import cz.kutner.comicsdb.fragment.ForumFragment;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class ClassifiedActivity extends AbstractActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Bazar");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ClassifiedFragment())
                    .commit();
        }
    }
}
