package cz.kutner.comicsdb.adapter

import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.ComicsDetailActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.model.Author

class AuthorDetailRVAdapter(private var author: Author) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ComicsViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var comicsName: TextView = itemView.findViewById(R.id.comics_name) as TextView
        internal var comicsPublished: TextView = itemView.findViewById(R.id.comics_published) as TextView
        internal var comicsRating: TextView = itemView.findViewById(R.id.comics_rating) as TextView
        internal var card_view_comics: CardView = itemView.findViewById(R.id.card_view_comics) as CardView
        internal var comicsId: Int? = null

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

    class AuthorViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView = itemView.findViewById(R.id.name) as TextView
        internal var country: TextView = itemView.findViewById(R.id.country) as TextView
        internal var bio: TextView = itemView.findViewById(R.id.bio) as TextView
        internal var notes: TextView = itemView.findViewById(R.id.notes) as TextView
        internal var photo: ImageView = itemView.findViewById(R.id.authorPhoto) as ImageView
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return 0 //záznam autora
            else -> return 1 //komiks
        }
    }

    override fun getItemCount(): Int {
        return author.comicses.size + 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val listViewItemType = getItemViewType(i)
        var v: RecyclerView.ViewHolder = AuthorViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_author_detail, viewGroup, false))
        when (listViewItemType) {
        //0 ->  //autor, ale toho už máme
            1 -> v = ComicsViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_comics, viewGroup, false)) //comics
        }
        return v
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (i == 0) {
            // zaznam autora
            val vh = viewHolder as AuthorViewHolder
            vh.name.text = author.name
            vh.bio.text = Html.fromHtml(author.bio)
            vh.country.text = author.country
            vh.notes.text = Html.fromHtml(author.notes)
            Picasso.with(ComicsDBApplication.context).load(author.photoUrl).into(vh.photo)

        } else {
            val j = i - 1 //i je o 1 větší, tak to musíme zmenšit, kvůli poli
            val vh = viewHolder as ComicsViewHolder
            vh.comicsName.text = author.comicses[j].name
            vh.comicsPublished.text = author.comicses[j].published
            vh.comicsRating.text = author.comicses[j].rating.toString()
            vh.comicsName.text = author.comicses[j].name
            vh.comicsId = author.comicses[j].id
        }
    }
}
