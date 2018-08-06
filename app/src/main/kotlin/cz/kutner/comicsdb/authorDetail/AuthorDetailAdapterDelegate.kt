package cz.kutner.comicsdb.authorDetail

import androidx.recyclerview.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.FragmentAuthorDetailBinding
import cz.kutner.comicsdb.model.AuthorDetail
import cz.kutner.comicsdb.model.Item
import kotlinx.android.synthetic.main.fragment_author_detail.view.*


class AuthorDetailAdapterDelegate(val inflater: LayoutInflater) : AdapterDelegate<List<Item>>() {
    override fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val vh = holder as AuthorViewHolder
        val author = items[position] as AuthorDetail

        vh.bind(author)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val authorBinding = FragmentAuthorDetailBinding.inflate(inflater, parent, false)
        return AuthorViewHolder(authorBinding)
    }

    override fun isForViewType(items: List<Item>, position: Int) = items[position] is AuthorDetail

    class AuthorViewHolder(val binding: FragmentAuthorDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.bio.movementMethod = LinkMovementMethod.getInstance()
            itemView.notes.movementMethod = LinkMovementMethod.getInstance()
        }

        fun bind(author: AuthorDetail) {
            binding.authorDetail = author
            binding.executePendingBindings()
        }
    }
}