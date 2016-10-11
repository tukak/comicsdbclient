package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.model.Series
import kotlinx.android.synthetic.main.fragment_series_detail.view.*


class SeriesAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh = holder as SeriesViewHolder
        val series = items[position] as Series
        vh.name.text = series.name
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return SeriesViewHolder(inflater.inflate(R.layout.fragment_series_detail, parent, false))
    }

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Series
    }

    class SeriesViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView = itemView.name
    }
}