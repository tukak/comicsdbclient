package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.ComicsDetailActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.model.Item
import kotlinx.android.synthetic.main.list_item_comics.view.*

class ComicsListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>> {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Comics
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder) {
        val vh: ComicsViewHolder = holder as ComicsViewHolder
        val comics: Comics = items[position] as Comics

        vh.comicsName.text = comics.name
        vh.comicsPublished.text = comics.published
        if (comics.rating.toInt() > 0) {
            vh.comicsRating.text = comics.rating.toString()
        } else {
            vh.comicsRating.text = " "
        }
        vh.comicsId = comics.id
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return ComicsViewHolder(inflater.inflate(R.layout.list_item_comics, parent, false))
    }

    internal class ComicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var comicsName: TextView = itemView.comics_name
        internal var comicsPublished: TextView = itemView.comics_published
        internal var comicsRating: TextView = itemView.comics_rating
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
    }

}