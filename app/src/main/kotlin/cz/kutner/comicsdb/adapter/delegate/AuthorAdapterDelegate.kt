package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.FragmentAuthorDetailBinding
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.model.Item


class AuthorAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {

    private val inflater: LayoutInflater = activity.layoutInflater

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh = holder as AuthorViewHolder
        val author = items[position] as Author

        vh.bind(author)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val authorBinding = FragmentAuthorDetailBinding.inflate(inflater, parent, false)
        return AuthorViewHolder(authorBinding)
    }

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Author
    }

    class AuthorViewHolder(val binding: FragmentAuthorDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(author: Author) {
            binding.author = author
            binding.executePendingBindings()
        }
    }
}