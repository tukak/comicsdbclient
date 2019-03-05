package cz.kutner.comicsdb.main

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.core.view.LayoutInflaterCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.context.IconicsLayoutInflater2
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.about.AboutFragment
import cz.kutner.comicsdb.authorList.AuthorListFragment
import cz.kutner.comicsdb.classifiedList.ClassifiedListFragment
import cz.kutner.comicsdb.comicsList.ComicsListFragment
import cz.kutner.comicsdb.forumList.ForumListFragment
import cz.kutner.comicsdb.newsList.NewsListFragment
import cz.kutner.comicsdb.search.SearchActivity
import cz.kutner.comicsdb.seriesList.SeriesListFragment
import kotlinx.android.synthetic.main.activity.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, IconicsLayoutInflater2(delegate))
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ComicsListFragment.newInstance()).commit()
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
            toolbar.navigationIcon = IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_menu)
                .color(Color.WHITE).sizeDp(24)
            toolbar.setNavigationOnClickListener { drawer_layout.openDrawer(GravityCompat.START) }
        }
    }

    private fun setDrawerIcons() {
        val menu = navigation_view.menu
        menu.findItem(R.id.navigation_item_comics).icon =
                IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_book_image).sizeDp(24)
        menu.findItem(R.id.navigation_item_news).icon =
                IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_comment_alert).sizeDp(24)
        menu.findItem(R.id.navigation_item_series).icon =
                IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_format_list_bulleted)
                    .sizeDp(24)
        menu.findItem(R.id.navigation_item_author).icon =
                IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_edit).sizeDp(24)
        menu.findItem(R.id.navigation_item_classified).icon =
                IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_money).sizeDp(24)
        menu.findItem(R.id.navigation_item_forum).icon =
                IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_comments).sizeDp(24)
        menu.findItem(R.id.navigation_item_about).icon =
                IconicsDrawable(this).icon(MaterialDesignIconic.Icon.gmi_info).sizeDp(24)
    }

    private fun setActionsForDrawer() {
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            val fragment: Fragment = when (menuItem.itemId) {
                R.id.navigation_item_comics -> ComicsListFragment.newInstance()
                R.id.navigation_item_news -> NewsListFragment.newInstance()
                R.id.navigation_item_series -> SeriesListFragment.newInstance()
                R.id.navigation_item_author -> AuthorListFragment.newInstance()
                R.id.navigation_item_classified -> ClassifiedListFragment.newInstance()
                R.id.navigation_item_forum -> ForumListFragment.newInstance()
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(navigation_view)) {
            super.onBackPressed()
        } else {
            drawer_layout.openDrawer(navigation_view)
        }

    }
}
