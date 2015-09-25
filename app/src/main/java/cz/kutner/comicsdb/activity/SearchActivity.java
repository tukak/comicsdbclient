package cz.kutner.comicsdb.activity;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.SearchEvent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.adapter.SearchPagerAdapter;

public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;
    FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        fragmentPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager(), getIntent());
        pager.setAdapter(fragmentPagerAdapter);
        slidingTabs.setupWithViewPager(pager);
        String query = getIntent().getStringExtra(SearchManager.QUERY);
        Answers.getInstance().logSearch(new SearchEvent()
                .putQuery(query));
        Tracker tracker = ComicsDBApplication.getTracker();
        tracker.send(new HitBuilders.EventBuilder().setCategory("Search").setAction(query).build());
    }
}
