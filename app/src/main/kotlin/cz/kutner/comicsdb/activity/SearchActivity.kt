package cz.kutner.comicsdb.activity

import android.app.SearchManager
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.SearchPagerAdapter
import cz.kutner.comicsdb.di.RetrofitModule
import kotlinx.android.synthetic.main.activity_tabbed.*
import space.traversal.kapsule.Injects

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val fragmentPagerAdapter: FragmentStatePagerAdapter = SearchPagerAdapter(supportFragmentManager, intent)
        pager.adapter = fragmentPagerAdapter
        sliding_tabs.setupWithViewPager(pager)
        val query = intent.getStringExtra(SearchManager.QUERY)

        val bundle: Bundle = Bundle()
        val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, query)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle)

    }
}
