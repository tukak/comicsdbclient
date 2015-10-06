package cz.kutner.comicsdb.holder

import android.content.Intent
import android.view.View
import android.widget.TextView
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.ComicsDetailActivity
import cz.kutner.comicsdb.model.Comics
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_comics)
class ComicsViewHolder(view: View) : ItemViewHolder<Comics>(view) {

    @ViewId(R.id.comics_name)
    internal var comicsName: TextView? = null
    @ViewId(R.id.comics_published)
    internal var comicsPublished: TextView? = null
    @ViewId(R.id.comics_rating)
    internal var comicsRating: TextView? = null
    internal var comicsId: Int? = null

    init {
        view.findViewById(R.id.card_view_comics).setOnClickListener {
            val intent = Intent(view.context, ComicsDetailActivity::class.java)
            if (comicsId != null) {
                intent.putExtra(COMICS_ID, comicsId as Int)
                view.context.startActivity(intent)
            }
        }
    }

    override fun onSetValues(comics: Comics, positionInfo: PositionInfo) {
        comicsName?.text = comics.name
        comicsPublished?.text = comics.published
        if (comics.rating!!.toInt() > 0) {
            comicsRating?.text = comics.rating?.toString()
        } else {
            comicsRating?.text = " "
        }
        comicsId = comics.id
    }

    companion object {
        val COMICS_ID = "cz.kutner.comicsdbclient.comicsdbclient.comics_id"
    }
}
