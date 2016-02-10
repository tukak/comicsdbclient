package cz.kutner.comicsdb.activity

import android.os.Bundle

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.ComicsListFragment

class MainActivity : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, ComicsListFragment.newInstance()).commit()
        }
    }

    companion object {
        val COMICS_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.comics_id"
        val AUTHOR_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.author_id"
        val SERIES_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.series_id"
    }
}
