package cz.kutner.comicsdb.holder

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.loadUrl
import cz.kutner.comicsdb.model.Classified
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_classified) class ClassifiedViewHolder(view: View) : ItemViewHolder<Classified>(view) {
    @ViewId(R.id.classified_nick_icon)
    lateinit var classifiedNickIcon: ImageView
    @ViewId(R.id.classified_nick)
    lateinit var classifiedNick: TextView
    @ViewId(R.id.classified_category)
    lateinit var classifiedCategory: TextView
    @ViewId(R.id.classified_time)
    lateinit var classifiedTime: TextView
    @ViewId(R.id.classified_text)
    lateinit var classifiedText: TextView

    override fun onSetValues(classified: Classified, positionInfo: PositionInfo) {
        classifiedNick.text = classified.nick
        classifiedTime.text = classified.time
        classifiedText.text = Html.fromHtml(classified.text)
        classifiedNickIcon.loadUrl(classified.iconUrl)
        classifiedCategory.text = classified.category
    }
}
