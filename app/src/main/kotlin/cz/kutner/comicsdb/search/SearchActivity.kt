package cz.kutner.comicsdb.search

import android.os.Bundle
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.appcompat.app.AppCompatActivity
import cz.kutner.comicsdb.R
import kotlinx.android.synthetic.main.activity_tabbed.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val fragmentPagerAdapter: FragmentStatePagerAdapter =
            SearchPagerAdapter(supportFragmentManager, intent, applicationContext)
        pager.adapter = fragmentPagerAdapter
        sliding_tabs.setupWithViewPager(pager)
    }
}
