package cz.kutner.comicsdb.activity

import android.app.SearchManager
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.SearchEvent
import com.google.android.gms.analytics.HitBuilders
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.SearchPagerAdapter
import kotlinx.android.synthetic.activity_search.*

public class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val actionBar = supportActionBar
        actionBar.setDisplayHomeAsUpEnabled(true)
        var fragmentPagerAdapter: FragmentPagerAdapter = SearchPagerAdapter(supportFragmentManager, intent)
        pager.adapter = fragmentPagerAdapter
        sliding_tabs.setupWithViewPager(pager)
        val query = intent.getStringExtra(SearchManager.QUERY)
        Answers.getInstance().logSearch(SearchEvent().putQuery(query))
        val tracker = ComicsDBApplication.getTracker()
        tracker.send(HitBuilders.EventBuilder().setCategory("Search").setAction(query).build())
    }
}
