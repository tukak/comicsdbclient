package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.FragmentComicsDetailBinding
import cz.kutner.comicsdb.helpers.ClickHandlers
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.model.Item
import kotlinx.android.synthetic.main.fragment_comics_detail.view.*


class ComicsAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater
    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh = holder as ComicsViewHolder
        val comics = items[position] as Comics

        vh.bind(comics)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val comicsBinding = FragmentComicsDetailBinding.inflate(inflater, parent, false)
        comicsBinding.handlers = ClickHandlers()
        return ComicsViewHolder(comicsBinding)
    }

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Comics
    }

    class ComicsViewHolder(val binding: FragmentComicsDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.authors.movementMethod = LinkMovementMethod.getInstance()
            itemView.series.movementMethod = LinkMovementMethod.getInstance()
        }

        fun bind(comics: Comics) {
            binding.comics = comics
            binding.executePendingBindings()
        }

    }
}