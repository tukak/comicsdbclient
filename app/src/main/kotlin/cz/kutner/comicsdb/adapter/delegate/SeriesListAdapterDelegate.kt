package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.activity.SeriesDetailActivity
import cz.kutner.comicsdb.databinding.ListItemSeriesBinding
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.model.Series
import kotlinx.android.synthetic.main.list_item_series.view.*

class SeriesListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater
    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Series
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh: SeriesViewHolder = holder as SeriesViewHolder
        val series: Series = items[position] as Series

        vh.seriesId = series.id
        vh.bind(series)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val seriesBinding = ListItemSeriesBinding.inflate(inflater, parent, false)
        return SeriesViewHolder(seriesBinding)
    }

    internal class SeriesViewHolder(val binding: ListItemSeriesBinding) : RecyclerView.ViewHolder(binding.root) {
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

        fun bind(series: Series) {
            binding.series = series
            binding.executePendingBindings()
        }
    }

}