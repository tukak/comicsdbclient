package cz.kutner.comicsdb.comicsList

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.ListItemComicsBinding
import cz.kutner.comicsdb.helpers.ClickHandlers
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.model.Item

class ComicsListAdapterDelegate(val inflater: LayoutInflater) : AdapterDelegate<List<Item>>() {
    override fun isForViewType(items: List<Item>, position: Int) = items[position] is Comics

    override fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val vh: ComicsViewHolder = holder as ComicsViewHolder
        val comics: Comics = items[position] as Comics

        vh.bind(comics)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemBinding = ListItemComicsBinding.inflate(inflater, parent, false)
        itemBinding.handlers = ClickHandlers()
        return ComicsViewHolder(itemBinding)
    }

    internal class ComicsViewHolder(val binding: ListItemComicsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comics: Comics) {
            binding.comics = comics
            binding.executePendingBindings()
        }
    }

}