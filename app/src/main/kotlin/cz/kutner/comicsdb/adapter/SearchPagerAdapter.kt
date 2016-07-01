package cz.kutner.comicsdb.adapter

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.AuthorFragment
import cz.kutner.comicsdb.fragment.ComicsListFragment
import cz.kutner.comicsdb.fragment.SeriesFragment
import cz.kutner.comicsdb.utils.Utils

class SearchPagerAdapter(fm: FragmentManager, val intent: Intent) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment
        when (position) {
            0 -> fragment = ComicsListFragment.newInstance()
            1 -> fragment = SeriesFragment.newInstance()
            2 -> fragment = AuthorFragment.newInstance()
            else -> throw Exception("Chybný fragment ve vyhledávání")
        }
        fragment.arguments = intent.extras
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        var name: CharSequence = ""
        when (position) {
            0 -> name = Utils.context.resources.getString(R.string.comics)
            1 -> name = Utils.context.resources.getString(R.string.series)
            2 -> name = Utils.context.resources.getString(R.string.author)
        }
        return name
    }

    override fun getCount(): Int {
        return 3
    }
}
