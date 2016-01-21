package cz.kutner.comicsdb.holder

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Classified
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_classified)
public class ClassifiedViewHolder(view: View) : ItemViewHolder<Classified>(view) {
    @ViewId(R.id.classified_nick_icon)
    var classifiedNickIcon: ImageView? = null
    @ViewId(R.id.classified_nick)
    var classifiedNick: TextView? = null
    @ViewId(R.id.classified_category)
    var classifiedCategory: TextView? = null
    @ViewId(R.id.classified_time)
    var classifiedTime: TextView? = null
    @ViewId(R.id.classified_text)
    var classifiedText: TextView? = null

    override fun onSetValues(classified: Classified, positionInfo: PositionInfo) {
        classifiedNick?.text = classified.nick
        classifiedTime?.text = classified.time
        classifiedText?.text = Html.fromHtml(classified.text)
        Picasso.with(ComicsDBApplication.context).load(classified.iconUrl).into(classifiedNickIcon)
        classifiedCategory?.text = classified.category
    }
}
