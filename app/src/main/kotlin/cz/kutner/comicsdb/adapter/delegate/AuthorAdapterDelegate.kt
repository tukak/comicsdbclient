package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.utils.Utils
import cz.kutner.comicsdb.utils.loadUrl
import kotlinx.android.synthetic.main.fragment_author_detail.view.*


class AuthorAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>> {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder) {
        val vh = holder as AuthorViewHolder
        val author = items[position] as Author
        vh.name.text = author.name
        vh.bio.text = Utils.fromHtml(author.bio)
        vh.country.text = author.country
        vh.notes.text = Utils.fromHtml(author.notes)
        vh.photo.loadUrl(author.photoUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return AuthorViewHolder(inflater.inflate(R.layout.fragment_author_detail, parent, false))
    }

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Author
    }

    class AuthorViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView = itemView.name
        internal var country: TextView = itemView.country
        internal var bio: TextView = itemView.bio
        internal var notes: TextView = itemView.notes
        internal var photo: ImageView = itemView.authorPhoto
    }
}