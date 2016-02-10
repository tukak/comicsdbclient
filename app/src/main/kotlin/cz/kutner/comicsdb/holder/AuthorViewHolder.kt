package cz.kutner.comicsdb.holder

import android.content.Intent
import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.AuthorDetailActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.model.Author
import org.jetbrains.anko.onClick
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_authors) class AuthorViewHolder(view: View) : ItemViewHolder<Author>(view) {
    @ViewId(R.id.authorName)
    lateinit var authorName: TextView
    @ViewId(R.id.authorCountry)
    lateinit var authorCountry: TextView
    @ViewId(R.id.card_view_authors)
    internal lateinit var card_view_authors: CardView
    internal var authorId: Int? = null


    init {
        card_view_authors.onClick {
            val intent = Intent(view.context, AuthorDetailActivity::class.java)
            if (authorId != null) {
                intent.putExtra(MainActivity.AUTHOR_ID, authorId as Int)
                view.context.startActivity(intent)
            }
        }
    }

    override fun onSetValues(author: Author, positionInfo: PositionInfo) {
        authorName.text = author.name
        authorCountry.text = author.country
        authorId = author.id
    }
}
