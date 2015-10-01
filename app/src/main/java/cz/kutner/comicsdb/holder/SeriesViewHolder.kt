package cz.kutner.comicsdb.holder

import android.view.View
import android.widget.TextView

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Series
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

    override fun onSetValues(series: Series, positionInfo: PositionInfo) {
        seriesName!!.text = series.name
        seriesNumberOfComicses!!.text = series.numberOfComicses!!.toString()
    }
}
