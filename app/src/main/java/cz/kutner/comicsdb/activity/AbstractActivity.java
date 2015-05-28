package cz.kutner.comicsdb.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public abstract class AbstractActivity extends AppCompatActivity {
    final String LOG_TAG = getClass().getSimpleName();
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Class cls = this.getClass();
    String[] activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities = getResources().getStringArray(R.array.nav_drawer_activities);
        setContentView(R.layout.activity);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        // Set the adapter for the list view
        drawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, getResources().getStringArray(R.array.nav_drawer_items)));
        drawerList.setOnItemClickListener(new SlideMenuClickListener());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // calling onPrepareOptionsMenu() to hide action bar icons
                ListView drawer = (ListView) drawerView.findViewById(R.id.left_drawer);
                for (int i = 0; i < drawer.getChildCount(); i++) {
                    View view = drawer.getChildAt(i);
                    TextView textView = (TextView) view.findViewById(R.id.navigation_drawer_text);
                    Class activity = null;
                    try {
                        activity = Class.forName("cz.kutner.comicsdb.activity." + activities[i]);
                    } catch (ClassNotFoundException e) {
                        Log.e(LOG_TAG, e.getMessage(), e);
                    }
                    if (cls == activity) {
                        textView.setBackgroundColor(Color.LTGRAY);
                    } else {
                        textView.setBackgroundColor(Color.WHITE);
                    }
                }
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent;
            Class activity = null;
            String[] activities = getResources().getStringArray(R.array.nav_drawer_activities);
            try {
                activity = Class.forName("cz.kutner.comicsdb.activity." + activities[position]);
            } catch (ClassNotFoundException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            if (activity != cls) {
                intent = new Intent(view.getContext(), activity);
                view.getContext().startActivity(intent);
                drawerLayout.closeDrawers();
            }
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
