package cz.kutner.comicsdb.comicsDetail

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import cz.kutner.comicsdb.databinding.ListItemCommentBinding
import cz.kutner.comicsdb.model.Comment
import cz.kutner.comicsdb.model.Item


class CommentsAdapterDelegate(val inflater: LayoutInflater) : AdapterDelegate<List<Item>>() {
    override fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val vh = holder as CommentsViewHolder
        val comment = items[position] as Comment
        vh.bind(comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val commentBinding = ListItemCommentBinding.inflate(inflater, parent, false)
        return CommentsViewHolder(commentBinding)
    }

    override fun isForViewType(items: List<Item>, position: Int) = items[position] is Comment

    class CommentsViewHolder(val binding: ListItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.comment = comment
            binding.executePendingBindings()
        }
    }
}