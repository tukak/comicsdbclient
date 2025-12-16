package cz.kutner.comicsdb.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import cz.kutner.comicsdb.databinding.ActivityTabbedBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTabbedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabbedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val adapter = SearchPagerAdapter(this, intent, applicationContext)
        binding.pager.adapter = adapter
        TabLayoutMediator(binding.slidingTabs, binding.pager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}
