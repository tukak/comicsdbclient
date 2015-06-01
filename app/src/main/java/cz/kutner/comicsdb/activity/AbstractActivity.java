package cz.kutner.comicsdb.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
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
import android.widget.ListView;
import android.widget.SearchView;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public abstract class AbstractActivity extends AppCompatActivity {
    final String LOG_TAG = getClass().getSimpleName();
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Class cls = this.getClass();
    String[] activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationViewItemSelectedListener());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.abc_ic_menu_share_mtrl_alpha);
            //TODO ???
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                navigationView = (NavigationView) findViewById(R.id.navigation_view);
                int menuId = 0;
                switch (cls.getSimpleName()) {
                    case "MainActivity":
                        Log.i(LOG_TAG, "JOOJOJO");
                        menuId = R.id.navigation_item_comics;
                        break;
                    case "ClassifiedActivity":
                        menuId = R.id.navigation_item_classified;
                        break;
                    case "ForumActivity":
                        menuId = R.id.navigation_item_forum;
                        break;
                }
                Log.i(LOG_TAG, cls.getSimpleName());
                Log.i(LOG_TAG, String.valueOf(menuId));
                Log.i(LOG_TAG, String.valueOf(R.id.navigation_item_comics));
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private class NavigationViewItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            Class activity = null;
            switch (menuItem.getItemId()) {
                case R.id.navigation_item_comics:
                    activity = MainActivity.class;
                    break;
                case R.id.navigation_item_classified:
                    activity = ClassifiedActivity.class;
                    break;
                case R.id.navigation_item_forum:
                    activity = ForumActivity.class;
                    break;
                case R.id.navigation_item_about:
                    activity = AboutActivity.class;
                    break;
            }
            //if (activity != cls) {
            //    Intent intent = new Intent(getApplication(), activity);
            //    startActivity(intent);
            //    drawerLayout.closeDrawers();
            //}
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SearchView searchView =
                (SearchView) toolbar.findViewById(R.id.searchView);
        ComponentName cn = new ComponentName(this, ComicsSearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
