package cz.kutner.comicsdb.activity

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.*
import kotlinx.android.synthetic.activity.drawer_layout
import kotlinx.android.synthetic.activity.navigation_view
import kotlinx.android.synthetic.toolbar.searchView
import kotlinx.android.synthetic.toolbar.toolbar


public abstract class AbstractActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        setupToolbar()
        setActionsForDrawer()
    }


    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)

            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
            toolbar.setNavigationOnClickListener { v -> drawer_layout.openDrawer(GravityCompat.START) }
        }
    }

    private fun setActionsForDrawer() {
        navigation_view.setNavigationItemSelectedListener { menuItem ->

            var fragment: Fragment? = null
            when (menuItem.itemId) {
                R.id.navigation_item_comics -> fragment = ComicsListFragment.newInstance()
                R.id.navigation_item_news -> fragment = NewsFragment.newInstance()
                R.id.navigation_item_series -> fragment = SeriesFragment.newInstance()
                R.id.navigation_item_author -> fragment = AuthorFragment.newInstance()
                R.id.navigation_item_classified -> fragment = ClassifiedFragment.newInstance()
                R.id.navigation_item_forum -> fragment = ForumFragment.newInstance()
                R.id.navigation_item_about -> fragment = AboutFragment.newInstance()
            }
            menuItem.setChecked(true)
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            }
            drawer_layout.closeDrawers()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val cn = ComponentName(this, SearchActivity::class.java)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn))

        return true
    }
}
