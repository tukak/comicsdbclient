package cz.kutner.comicsdb.activity

import android.os.Bundle

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.ComicsListFragment

public class MainActivity : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, ComicsListFragment.newInstance()).commit()
        }
    }

    companion object {
        public val COMICS_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.comics_id"
        public val AUTHOR_ID: String = "cz.kutner.comicsdbclient.comicsdbclient.author_id"
    }
}
