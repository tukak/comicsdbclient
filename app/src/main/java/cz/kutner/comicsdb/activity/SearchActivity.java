package cz.kutner.comicsdb.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
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


    }
}
