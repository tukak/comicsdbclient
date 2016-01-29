package cz.kutner.comicsdb.activity

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.AuthorDetailFragment

public class AuthorDetailActivity : AbstractDetailActivity() {

    override val fragment = AuthorDetailFragment.newInstance()
    override val prefix by lazy { getString(R.string.url_author_detail) }
    override val extraName = MainActivity.AUTHOR_ID
}
