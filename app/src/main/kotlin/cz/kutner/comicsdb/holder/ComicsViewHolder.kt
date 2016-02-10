package cz.kutner.comicsdb.holder

import android.content.Intent
import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.activity.ComicsDetailActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.model.Comics
import org.jetbrains.anko.onClick
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_comics)
class ComicsViewHolder(view: View) : ItemViewHolder<Comics>(view) {

    @ViewId(R.id.comics_name)
    internal lateinit var comicsName: TextView
    @ViewId(R.id.comics_published)
    internal lateinit var comicsPublished: TextView
    @ViewId(R.id.comics_rating)
    internal lateinit var comicsRating: TextView
    internal var comicsId: Int? = null
    @ViewId(R.id.card_view_comics)
    internal lateinit var card_view_comics: CardView

    init {
        card_view_comics.onClick {
            val intent = Intent(view.context, ComicsDetailActivity::class.java)
            if (comicsId != null) {
                intent.putExtra(MainActivity.COMICS_ID, comicsId as Int)
                view.context.startActivity(intent)
            }
        }
    }

    override fun onSetValues(comics: Comics, positionInfo: PositionInfo) {
        comicsName.text = comics.name
        comicsPublished.text = comics.published
        if (comics.rating.toInt() > 0) {
            comicsRating.text = comics.rating.toString()
        } else {
            comicsRating.text = " "
        }
        comicsId = comics.id
    }
}
