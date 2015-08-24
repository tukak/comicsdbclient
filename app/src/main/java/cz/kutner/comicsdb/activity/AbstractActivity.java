package cz.kutner.comicsdb.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.fragment.AboutFragment;
import cz.kutner.comicsdb.fragment.AuthorFragment;
import cz.kutner.comicsdb.fragment.ClassifiedFragment;
import cz.kutner.comicsdb.fragment.ComicsListFragment;
import cz.kutner.comicsdb.fragment.ForumFragment;
import cz.kutner.comicsdb.fragment.NewsFragment;
import cz.kutner.comicsdb.fragment.SeriesFragment;

public abstract class AbstractActivity extends AppCompatActivity {
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
        setActionsForDrawer();
    }


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    private void setActionsForDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_comics:
                        fragment = ComicsListFragment.newInstance();
                        break;
                    case R.id.navigation_item_news:
                        fragment = NewsFragment.newInstance();
                        break;
                    case R.id.navigation_item_series:
                        fragment = SeriesFragment.newInstance();
                        break;
                    case R.id.navigation_item_author:
                        fragment = AuthorFragment.newInstance();
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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName cn = new ComponentName(this, ComicsSearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        return true;
    }
}
