package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.activity.SeriesDetailActivity
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.model.Series
import kotlinx.android.synthetic.main.list_item_series.view.*

class SeriesListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>> {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Series
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder) {
        val vh: SeriesViewHolder = holder as SeriesViewHolder
        val series: Series = items[position] as Series

        vh.seriesName.text = series.name
        vh.seriesNumberOfComicses.text = series.numberOfComicses?.toString()
        vh.seriesId = series.id
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return SeriesViewHolder(inflater.inflate(R.layout.list_item_series, parent, false))
    }

    internal class SeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var seriesName: TextView = itemView.series_name
        var seriesNumberOfComicses: TextView = itemView.seriesNumberOfComicses
        internal var card_view_series: CardView = itemView.card_view_series
        internal var seriesId: Int? = null
        init {
            card_view_series.setOnClickListener {
                val intent = Intent(itemView.context, SeriesDetailActivity::class.java)
                if (seriesId != null) {
                    intent.putExtra(MainActivity.SERIES_ID, seriesId as Int)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}