package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.FragmentSeriesDetailBinding
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.model.SeriesDetail


class SeriesAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater
    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh = holder as SeriesViewHolder
        val series = items[position] as SeriesDetail
        vh.bind(series)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val seriesBinding = FragmentSeriesDetailBinding.inflate(inflater, parent, false)
        return SeriesViewHolder(seriesBinding)
    }

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is SeriesDetail
    }

    class SeriesViewHolder(val binding: FragmentSeriesDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(series: SeriesDetail) {
            binding.seriesDetail = series
            binding.executePendingBindings()
        }
    }
}