package cz.kutner.comicsdb.adapter

import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.ComicsDetailActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.model.Series
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick

class SeriesDetailRVAdapter(private var series: Series) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ComicsViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var comicsName: TextView = itemView.find<TextView>(R.id.comics_name)
        internal var comicsPublished: TextView = itemView.find<TextView>(R.id.comics_published)
        internal var comicsRating: TextView = itemView.find<TextView>(R.id.comics_rating)
        internal var card_view_comics: CardView = itemView.find<CardView>(R.id.card_view_comics)
        internal var comicsId: Int? = null

        init {
            card_view_comics.onClick {
                val intent = Intent(itemView.context, ComicsDetailActivity::class.java)
                if (comicsId != null) {
                    intent.putExtra(MainActivity.COMICS_ID, comicsId as Int)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    class SeriesViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView = itemView.find<TextView>(R.id.name)
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return 0 //záznam autora
            else -> return 1 //komiks
        }
    }

    override fun getItemCount(): Int {
        return series.comicses.size + 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val listViewItemType = getItemViewType(i)
        var v: RecyclerView.ViewHolder = SeriesViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_series_detail, viewGroup, false))
        when (listViewItemType) {
        //0 ->  //série, ale toho už máme
            1 -> v = ComicsViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_comics, viewGroup, false)) //comics
        }
        return v
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (i == 0) {
            // zaznam serie
            val vh = viewHolder as SeriesViewHolder
            vh.name.text = series.name
        } else {
            val j = i - 1 //i je o 1 větší, tak to musíme zmenšit, kvůli poli
            val vh = viewHolder as ComicsViewHolder
            vh.comicsName.text = series.comicses[j].name
            vh.comicsPublished.text = series.comicses[j].published
            vh.comicsRating.text = series.comicses[j].rating.toString()
            vh.comicsName.text = series.comicses[j].name
            vh.comicsId = series.comicses[j].id
        }
    }
}
