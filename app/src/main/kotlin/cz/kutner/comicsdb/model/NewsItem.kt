package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml

data class NewsItem(val title: String?, val nick: String?, val text: String?, val time: String?) : Item {
    fun getTextFromHtml(): Spanned? = text?.fromHtml()
}
