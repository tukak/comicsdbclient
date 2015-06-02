package cz.kutner.comicsdb.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import cz.kutner.comicsdb.fragment.ClassifiedFragment;
import cz.kutner.comicsdb.fragment.ComicsListFragment;
import cz.kutner.comicsdb.fragment.ForumFragment;
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
