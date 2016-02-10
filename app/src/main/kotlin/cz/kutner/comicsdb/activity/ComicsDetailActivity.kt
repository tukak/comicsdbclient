package cz.kutner.comicsdb.activity

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.ComicsDetailFragment

class ComicsDetailActivity : AbstractDetailActivity() {

    override val fragment = ComicsDetailFragment.newInstance()
    override val prefix by lazy { getString(R.string.url_comics_detail) }
    override val extraName = MainActivity.COMICS_ID

}
