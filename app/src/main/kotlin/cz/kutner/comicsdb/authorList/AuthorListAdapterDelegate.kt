package cz.kutner.comicsdb.authorList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.ListItemAuthorsBinding
import cz.kutner.comicsdb.helpers.ClickHandlers
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.model.Item

class AuthorListAdapterDelegate(val inflater: LayoutInflater) : AdapterDelegate<List<Item>>() {

    override fun isForViewType(items: List<Item>, position: Int) = items[position] is Author

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh: AuthorViewHolder = holder as AuthorViewHolder
        val author: Author = items[position] as Author

        vh.bind(author)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val authorBinding = ListItemAuthorsBinding.inflate(inflater, parent, false)
        authorBinding.handlers = ClickHandlers()
        return AuthorViewHolder(authorBinding)
    }

    internal class AuthorViewHolder(val binding: ListItemAuthorsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(author: Author) {
            binding.author = author
            binding.executePendingBindings()
        }
    }

}