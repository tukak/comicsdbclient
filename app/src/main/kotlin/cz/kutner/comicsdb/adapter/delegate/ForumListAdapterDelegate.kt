package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.ListItemForumBinding
import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.model.Item


class ForumListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater
    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is ForumEntry
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh: ForumEntryViewHolder = holder as ForumEntryViewHolder
        val forumEntry: ForumEntry = items[position] as ForumEntry
        vh.bind(forumEntry)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val forumBinding = ListItemForumBinding.inflate(inflater, parent, false)
        return ForumEntryViewHolder(forumBinding)
    }

    internal class ForumEntryViewHolder(val binding: ListItemForumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forumEntry: ForumEntry) {
            binding.forumEntry = forumEntry
            binding.executePendingBindings()
        }


    }

}