package cz.kutner.comicsdb.activity

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.context.IconicsLayoutInflater
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.*
import kotlinx.android.synthetic.main.activity.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory(layoutInflater, IconicsLayoutInflater(delegate))
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, ComicsListFragment.newInstance()).commit()
        }
        setContentView(R.layout.activity)
        setupToolbar()
        setDrawerIcons()
        setActionsForDrawer()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.navigationIcon = IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_menu).color(Color.WHITE).sizeDp(24)
            toolbar.setNavigationOnClickListener { drawer_layout.openDrawer(GravityCompat.START) }
        }
    }

    private fun setDrawerIcons() {
        val menu = navigation_view.menu
        menu.findItem(R.id.navigation_item_comics).icon = IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_book_image).sizeDp(24)
        menu.findItem(R.id.navigation_item_news).icon = IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_comment_alert).sizeDp(24)
        menu.findItem(R.id.navigation_item_series).icon = IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_format_list_bulleted).sizeDp(24)
        menu.findItem(R.id.navigation_item_author).icon = IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_edit).sizeDp(24)
        menu.findItem(R.id.navigation_item_classified).icon = IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_money).sizeDp(24)
        menu.findItem(R.id.navigation_item_forum).icon = IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_comments).sizeDp(24)
        menu.findItem(R.id.navigation_item_about).icon = IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_info).sizeDp(24)
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
        val searchView = menu.findItem(R.id.searchView).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn))
        return true
    }

    companion object {
        val COMICS_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.comics_id"
        val AUTHOR_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.author_id"
        val SERIES_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.series_id"
    }
}
