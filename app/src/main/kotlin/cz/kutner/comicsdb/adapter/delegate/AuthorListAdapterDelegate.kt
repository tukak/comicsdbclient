package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.AuthorDetailActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.model.Item
import kotlinx.android.synthetic.main.list_item_authors.view.*

class AuthorListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Author
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh: AuthorViewHolder = holder as AuthorViewHolder
        val author: Author = items[position] as Author

        vh.authorName.text = author.name
        vh.authorCountry.text = author.country
        vh.authorId = author.id
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        return AuthorViewHolder(inflater.inflate(R.layout.list_item_authors, parent, false))
    }

    internal class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var authorName: TextView = itemView.authorName
        internal var authorCountry: TextView = itemView.authorCountry
        internal var card_view_authors: CardView = itemView.card_view_authors
        internal var authorId: Int? = null

        init {
            card_view_authors.setOnClickListener {
                val intent = Intent(itemView.context, AuthorDetailActivity::class.java)
                if (authorId != null) {
                    intent.putExtra(MainActivity.AUTHOR_ID, authorId as Int)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}