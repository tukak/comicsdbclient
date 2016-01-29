package cz.kutner.comicsdb.activity

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.SeriesDetailFragment

public class SeriesDetailActivity : AbstractDetailActivity() {

    override val fragment = SeriesDetailFragment.newInstance()
    override val prefix by lazy { getString(R.string.url_series_detail) }
    override val extraName = MainActivity.SERIES_ID

}
