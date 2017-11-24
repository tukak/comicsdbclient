package cz.kutner.comicsdb.search

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.authorList.AuthorListFragment
import cz.kutner.comicsdb.comicsList.ComicsListFragment
import cz.kutner.comicsdb.seriesList.SeriesListFragment

class SearchPagerAdapter(fm: FragmentManager, private val intent: Intent, val context: Context) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            0 -> ComicsListFragment.newInstance()
            1 -> SeriesListFragment.newInstance()
            2 -> AuthorListFragment.newInstance()
            else -> throw Exception("Chybný fragment ve vyhledávání")
        }
        fragment.arguments = intent.extras
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        var name: CharSequence = ""
        when (position) {
            0 -> name = context.resources.getString(R.string.comics)
            1 -> name = context.resources.getString(R.string.series)
            2 -> name = context.resources.getString(R.string.author)
        }
        return name
    }

    override fun getCount(): Int = 3
}
