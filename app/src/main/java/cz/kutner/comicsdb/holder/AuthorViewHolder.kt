package cz.kutner.comicsdb.holder

import android.view.View
import android.widget.TextView

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Author
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_authors)
public class AuthorViewHolder(view: View) : ItemViewHolder<Author>(view) {
    @ViewId(R.id.authorName)
    var authorName: TextView? = null
    @ViewId(R.id.authorCountry)
    var authorCountry: TextView? = null

    override fun onSetValues(author: Author, positionInfo: PositionInfo) {
        authorName!!.text = author.name
        authorCountry!!.text = author.country
    }
}
