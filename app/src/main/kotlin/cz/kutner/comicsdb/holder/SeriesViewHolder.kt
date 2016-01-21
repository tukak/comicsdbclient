package cz.kutner.comicsdb.holder

import android.content.Intent
import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.activity.SeriesDetailActivity
import cz.kutner.comicsdb.model.Series
import org.jetbrains.anko.onClick
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_series)
public class SeriesViewHolder(view: View) : ItemViewHolder<Series>(view) {
    @ViewId(R.id.series_name)
    var seriesName: TextView? = null
    @ViewId(R.id.seriesNumberOfComicses)
    var seriesNumberOfComicses: TextView? = null
    @ViewId(R.id.card_view_series)
    internal var card_view_series: CardView? = null
    internal var seriesId: Int? = null

    init {
        card_view_series?.onClick {
            val intent = Intent(view.context, SeriesDetailActivity::class.java)
            if (seriesId != null) {
                intent.putExtra(MainActivity.SERIES_ID, seriesId as Int)
                view.context.startActivity(intent)
            }
        }
    }

    override fun onSetValues(series: Series, positionInfo: PositionInfo) {
        seriesName?.text = series.name
        seriesNumberOfComicses?.text = series.numberOfComicses?.toString()
        seriesId = series.id
    }
}
