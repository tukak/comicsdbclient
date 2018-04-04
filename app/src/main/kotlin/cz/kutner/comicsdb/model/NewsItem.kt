package cz.kutner.comicsdb.model

import android.text.Spanned
import androidx.core.text.parseAsHtml
import java.util.*

data class NewsItem(val title: String?, val nick: String, val text: String, val time: Date) : Item {
    fun getTextFromHtml(): Spanned? =
        text.replace("href='/", "href='http://www.comicsdb.cz/").parseAsHtml()
}
