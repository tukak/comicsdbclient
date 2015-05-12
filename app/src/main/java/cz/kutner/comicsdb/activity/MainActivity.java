package cz.kutner.comicsdb.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;

import cz.kutner.comicsdb.fragment.ComicsListFragment;
import cz.kutner.comicsdbclient.comicsdbclient.R;

public class MainActivity extends ActionBarActivity {
    public final static String COMICS_ID = "cz.kutner.comicsdbclient.comicsdbclient.comics_id";
    public final static String SEARCH_QUERY = "cz.kutner.comicsdbclient.comicsdbclient.search_query";
    private final String LOG_TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ComicsListFragment())
                    .commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
}
