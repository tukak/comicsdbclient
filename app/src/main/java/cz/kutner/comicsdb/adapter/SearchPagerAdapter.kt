package cz.kutner.comicsdb.adapter

import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.AuthorFragment
import cz.kutner.comicsdb.fragment.ComicsListFragment
import cz.kutner.comicsdb.fragment.SeriesFragment

public class SearchPagerAdapter(fm: FragmentManager, var intent:

Intent) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ComicsListFragment.newInstance()
            1 -> fragment = SeriesFragment.newInstance()
            2 -> fragment = AuthorFragment.newInstance()
        }
        fragment!!.arguments = intent.extras
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        var name: CharSequence = ""
        when (position) {
            0 -> name = ComicsDBApplication.getContext().resources.getString(R.string.comics)
            1 -> name = ComicsDBApplication.getContext().resources.getString(R.string.series)
            2 -> name = ComicsDBApplication.getContext().resources.getString(R.string.author)
        }
        return name
    }

    override fun getCount(): Int {
        return 3
    }
}
