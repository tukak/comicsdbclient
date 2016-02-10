package cz.kutner.comicsdb.activity

import android.app.SearchManager
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.SearchEvent
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.SearchPagerAdapter
import cz.kutner.comicsdb.di.Tracker
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {
    @Inject lateinit var tracker: Tracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as ComicsDBApplication).applicationComponent.inject(this)
        setContentView(R.layout.activity_search)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        var fragmentPagerAdapter: FragmentStatePagerAdapter = SearchPagerAdapter(supportFragmentManager, intent)
        pager.adapter = fragmentPagerAdapter
        sliding_tabs.setupWithViewPager(pager)
        val query = intent.getStringExtra(SearchManager.QUERY)
        Answers.getInstance().logSearch(SearchEvent().putQuery(query))
        tracker.logEvent(category = "Search", action = query)
    }
}
