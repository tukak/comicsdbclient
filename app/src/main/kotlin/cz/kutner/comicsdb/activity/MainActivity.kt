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
import kotlinx.android.synthetic.main.activity.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, ComicsListFragment.newInstance()).commit()
        }
        setContentView(R.layout.activity)
        setupToolbar()
        setActionsForDrawer()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
            toolbar.setNavigationOnClickListener { v -> drawer_layout.openDrawer(GravityCompat.START) }
        }
    }

    private fun setActionsForDrawer() {
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            val fragment: Fragment = when (menuItem.itemId) {
                R.id.navigation_item_comics -> ComicsListFragment.newInstance()
                R.id.navigation_item_news -> NewsFragment.newInstance()
                R.id.navigation_item_series -> SeriesFragment.newInstance()
                R.id.navigation_item_author -> AuthorFragment.newInstance()
                R.id.navigation_item_classified -> ClassifiedFragment.newInstance()
                R.id.navigation_item_forum -> ForumFragment.newInstance()
                R.id.navigation_item_about -> AboutFragment.newInstance()
                else -> ComicsListFragment.newInstance()
            }
            menuItem.isChecked = true
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            drawer_layout.closeDrawers()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val cn = ComponentName(this, SearchActivity::class.java)
     //   searchView.setSearchableInfo(searchManager.getSearchableInfo(cn))
        //TODO v androidu N se nezobrazuje psaný text při vyhledávání
        return true
    }

    companion object {
        val COMICS_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.comics_id"
        val AUTHOR_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.author_id"
        val SERIES_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.series_id"
    }
}
