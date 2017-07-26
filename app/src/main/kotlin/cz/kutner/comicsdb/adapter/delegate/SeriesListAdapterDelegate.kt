package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.ListItemSeriesBinding
import cz.kutner.comicsdb.helpers.ClickHandlers
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.model.Series

class SeriesListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater
    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Series
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh: SeriesViewHolder = holder as SeriesViewHolder
        val series: Series = items[position] as Series

        vh.bind(series)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val seriesBinding = ListItemSeriesBinding.inflate(inflater, parent, false)
        seriesBinding.handlers = ClickHandlers()
        return SeriesViewHolder(seriesBinding)
    }

    internal class SeriesViewHolder(val binding: ListItemSeriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(series: Series) {
            binding.series = series
            binding.executePendingBindings()
        }
    }

}