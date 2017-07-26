package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.activity.ComicsDetailActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.databinding.ListItemComicsBinding
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.model.Item
import kotlinx.android.synthetic.main.list_item_comics.view.*

class ComicsListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Comics
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh: ComicsViewHolder = holder as ComicsViewHolder
        val comics: Comics = items[position] as Comics

        vh.bind(comics)
        vh.comicsId = comics.id
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val itemBinding = ListItemComicsBinding.inflate(inflater, parent, false)
        return ComicsViewHolder(itemBinding)
    }

    internal class ComicsViewHolder(val binding: ListItemComicsBinding) : RecyclerView.ViewHolder(binding.root) {
        internal var comicsId: Int? = null
        internal var card_view_comics: CardView = itemView.card_view_comics

        init {
            card_view_comics.setOnClickListener {
                val intent = Intent(itemView.context, ComicsDetailActivity::class.java)
                if (comicsId != null) {
                    intent.putExtra(MainActivity.COMICS_ID, comicsId as Int)
                    itemView.context.startActivity(intent)
                }
            }
        }

        fun bind(comics: Comics) {
            binding.comics = comics
            binding.executePendingBindings()
        }
    }

}