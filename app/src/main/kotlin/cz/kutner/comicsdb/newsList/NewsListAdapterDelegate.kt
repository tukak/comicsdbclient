package cz.kutner.comicsdb.newsList

import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.ListItemNewsBinding
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.model.NewsItem
import kotlinx.android.synthetic.main.list_item_news.view.*

class NewsListAdapterDelegate(val inflater: LayoutInflater) : AdapterDelegate<List<Item>>() {
    override fun isForViewType(items: List<Item>, position: Int) = items[position] is NewsItem

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh: NewsViewHolder = holder as NewsViewHolder
        val newsItem: NewsItem = items[position] as NewsItem
        vh.bind(newsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val newsBinding = ListItemNewsBinding.inflate(inflater, parent, false)
        return NewsViewHolder(newsBinding)
    }

    internal class NewsViewHolder(val binding: ListItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.newsItemText.movementMethod = LinkMovementMethod.getInstance()
        }

        fun bind(newsItem: NewsItem) {
            binding.newsItem = newsItem
            binding.executePendingBindings()
        }
    }

}