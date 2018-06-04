package cz.kutner.comicsdb.model

import java.util.*

data class NewsItem(val title: String?, val nick: String, val text: String, val time: Date) : Item {
    fun getTextWithUrl(): String =
        text.replace("href='/", "href='http://www.comicsdb.cz/")
}
