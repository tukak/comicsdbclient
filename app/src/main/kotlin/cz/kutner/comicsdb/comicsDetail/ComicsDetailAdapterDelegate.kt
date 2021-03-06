package cz.kutner.comicsdb.comicsDetail

import androidx.recyclerview.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import cz.kutner.comicsdb.databinding.FragmentComicsDetailBinding
import cz.kutner.comicsdb.helpers.ClickHandlers
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.model.Item
import kotlinx.android.synthetic.main.comics_info.view.*
import kotlinx.android.synthetic.main.fragment_comics_detail.view.*


class ComicsDetailAdapterDelegate(val inflater: LayoutInflater) : AdapterDelegate<List<Item>>() {
    override fun onBindViewHolder(
        items: List<Item>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val vh = holder as ComicsViewHolder
        val comics = items[position] as ComicsDetail

        vh.bind(comics)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val comicsBinding = FragmentComicsDetailBinding.inflate(inflater, parent, false)
        comicsBinding.handlers = ClickHandlers()
        return ComicsViewHolder(comicsBinding)
    }

    override fun isForViewType(items: List<Item>, position: Int) = items[position] is ComicsDetail

    class ComicsViewHolder(val binding: FragmentComicsDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.authors.movementMethod = LinkMovementMethod.getInstance()
            itemView.series.movementMethod = LinkMovementMethod.getInstance()
            itemView.description.movementMethod = LinkMovementMethod.getInstance()
        }

        fun bind(comics: ComicsDetail) {
            binding.comicsDetail = comics
            binding.executePendingBindings()
        }

    }
}