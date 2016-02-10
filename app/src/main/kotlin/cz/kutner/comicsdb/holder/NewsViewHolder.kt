package cz.kutner.comicsdb.holder

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView

import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.NewsItem
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@LayoutId(R.layout.list_item_news) class NewsViewHolder(view: View) : ItemViewHolder<NewsItem>(view) {
    @ViewId(R.id.newsItemTitle)
    var newsItemTitle: TextView? = null
    @ViewId(R.id.newsItemNick)
    var newsItemNick: TextView? = null
    @ViewId(R.id.newsItemTime)
    var newsItemTime: TextView? = null
    @ViewId(R.id.newsItemText)
    var newsItemText: TextView? = null

    init {
        newsItemText?.movementMethod = LinkMovementMethod.getInstance()

    }

    override fun onSetValues(newsItem: NewsItem, positionInfo: PositionInfo) {
        newsItemNick?.text = newsItem.nick
        newsItemText?.text = Html.fromHtml(newsItem.text)
        newsItemTime?.text = newsItem.time
        newsItemTitle?.text = newsItem.title
    }
}
