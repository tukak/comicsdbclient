package cz.kutner.comicsdb.search

import android.os.Bundle
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.appcompat.app.AppCompatActivity
import cz.kutner.comicsdb.databinding.ActivityTabbedBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTabbedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabbedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val fragmentPagerAdapter: FragmentStatePagerAdapter =
            SearchPagerAdapter(supportFragmentManager, intent, applicationContext)
        binding.pager.adapter = fragmentPagerAdapter
        binding.slidingTabs.setupWithViewPager(binding.pager)
    }
}
