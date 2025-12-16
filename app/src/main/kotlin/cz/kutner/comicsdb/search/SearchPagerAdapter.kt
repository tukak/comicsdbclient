package cz.kutner.comicsdb.search

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.authorList.AuthorListFragment
import cz.kutner.comicsdb.comicsList.ComicsListFragment
import cz.kutner.comicsdb.seriesList.SeriesListFragment

class SearchPagerAdapter(activity: FragmentActivity, private val intent: Intent, val context: Context) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            0 -> ComicsListFragment.newInstance()
            1 -> SeriesListFragment.newInstance()
            2 -> AuthorListFragment.newInstance()
            else -> throw Exception("Chybny fragment ve vyhledavani")
        }
        fragment.arguments = intent.extras
        return fragment
    }

    fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.resources.getString(R.string.comics)
            1 -> context.resources.getString(R.string.series)
            2 -> context.resources.getString(R.string.author)
            else -> ""
        }
    }

    override fun getItemCount(): Int = 3
}
