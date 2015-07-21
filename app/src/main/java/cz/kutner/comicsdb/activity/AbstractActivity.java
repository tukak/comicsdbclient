package cz.kutner.comicsdb.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kutner.comicsdb.fragment.AboutFragment;
import cz.kutner.comicsdb.fragment.ClassifiedFragment;
import cz.kutner.comicsdb.fragment.ComicsListFragment;
import cz.kutner.comicsdb.fragment.ForumFragment;
import cz.kutner.comicsdb.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public abstract class AbstractActivity extends AppCompatActivity {
    final String LOG_TAG = getClass().getSimpleName();
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Bind(R.id.searchView)
    SearchView searchView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        ButterKnife.bind(this);

        setupToolbar();
        setupNavigationDrawer();
    }


    void setupToolbar() {
        setSupportActionBar(toolbar);
      /*  final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_menu_share_mtrl_alpha);
            //TODO ???
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
    }

    void setupNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationViewItemSelectedListener());
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private class NavigationViewItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            Fragment fragment = null;
            switch (menuItem.getItemId()) {
                case R.id.navigation_item_comics:
                    fragment = ComicsListFragment.newInstance();
                    break;
                case R.id.navigation_item_classified:
                    fragment = ClassifiedFragment.newInstance();
                    break;
                case R.id.navigation_item_forum:
                    fragment = ForumFragment.newInstance();
                    break;
                case R.id.navigation_item_about:
                    fragment = AboutFragment.newInstance();
                    break;
            }
            menuItem.setChecked(true);
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }
            drawerLayout.closeDrawers();
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName cn = new ComponentName(this, ComicsSearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        return true;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
