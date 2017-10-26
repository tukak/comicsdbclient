package cz.kutner.comicsdb.adapter.delegate

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.FragmentAuthorDetailBinding
import cz.kutner.comicsdb.model.AuthorDetail
import cz.kutner.comicsdb.model.Item


class AuthorAdapterDelegate(val inflater: LayoutInflater) : AdapterDelegate<List<Item>>() {
    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh = holder as AuthorViewHolder
        val author = items[position] as AuthorDetail

        vh.bind(author)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val authorBinding = FragmentAuthorDetailBinding.inflate(inflater, parent, false)
        return AuthorViewHolder(authorBinding)
    }

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is AuthorDetail
    }

    class AuthorViewHolder(val binding: FragmentAuthorDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(author: AuthorDetail) {
            binding.authorDetail = author
            binding.executePendingBindings()
        }
    }
}