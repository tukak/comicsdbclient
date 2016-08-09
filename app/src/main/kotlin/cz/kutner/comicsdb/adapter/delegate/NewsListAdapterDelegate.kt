package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.model.NewsItem
import cz.kutner.comicsdb.utils.Utils
import kotlinx.android.synthetic.main.list_item_news.view.*


class NewsListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>> {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is NewsItem
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder) {
        val vh: NewsViewHolder = holder as NewsViewHolder
        val newsItem: NewsItem = items[position] as NewsItem
        vh.newsItemNick.text = newsItem.nick
        vh.newsItemText.text = Utils.fromHtml(newsItem.text)
        vh.newsItemTime.text = newsItem.time
        vh.newsItemTitle.text = newsItem.title
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return NewsViewHolder(inflater.inflate(R.layout.list_item_news, parent, false))
    }

    internal class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsItemTitle: TextView = itemView.newsItemTitle
        var newsItemNick: TextView = itemView.newsItemNick
        var newsItemTime: TextView = itemView.newsItemTime
        var newsItemText: TextView = itemView.newsItemText

        init {
            newsItemText.movementMethod = LinkMovementMethod.getInstance()
        }
    }

}