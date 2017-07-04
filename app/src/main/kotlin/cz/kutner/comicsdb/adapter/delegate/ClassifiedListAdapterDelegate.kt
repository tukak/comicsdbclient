package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.utils.fromHtml
import cz.kutner.comicsdb.utils.loadUrl
import kotlinx.android.synthetic.main.list_item_classified.view.*


class ClassifiedListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Classified
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh: ClassifiedViewHolder = holder as ClassifiedViewHolder
        val classified: Classified = items[position] as Classified

        vh.classifiedNick.text = classified.nick
        vh.classifiedTime.text = classified.time
        vh.classifiedText.text = classified.text.fromHtml()
        vh.classifiedNickIcon.loadUrl(classified.iconUrl)
        vh.classifiedCategory.text = classified.category
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return ClassifiedViewHolder(inflater.inflate(R.layout.list_item_classified, parent, false))
    }

    internal class ClassifiedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var classifiedNickIcon: ImageView = itemView.classified_nick_icon
        var classifiedNick: TextView = itemView.classified_nick
        var classifiedCategory: TextView  = itemView.classified_category
        var classifiedTime: TextView  = itemView.classified_time
        var classifiedText: TextView  = itemView.classified_text

    }

}