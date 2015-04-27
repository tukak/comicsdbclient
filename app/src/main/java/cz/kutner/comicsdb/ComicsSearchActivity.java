package cz.kutner.comicsdb;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import cz.kutner.comicsdbclient.comicsdbclient.R;

public class ComicsSearchActivity extends ActionBarActivity {
    private final String LOG_TAG = ComicsSearchActivity.class.getSimpleName();
    public final static String COMICS_URL = "cz.kutner.comicsdbclient.comicsdbclient.comics_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_search);
        handleIntent(getIntent());
    }

    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Fragment fragment = new ComicsListFragment();
            Bundle args = new Bundle();
            args.putString("query", query);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Výsledek pro \"" + query + "\"");
            setSupportActionBar(toolbar);
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.search_container, fragment)
                    .commit();
        }
    }

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

    public void listItemOnClick(View v) {
        Intent intent = new Intent(this, ComicsDetailActivity.class);
        intent.putExtra(COMICS_URL, v.getTag().toString());
        startActivity(intent);
    }
}
