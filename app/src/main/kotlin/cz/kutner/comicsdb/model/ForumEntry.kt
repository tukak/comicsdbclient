package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml

data class ForumEntry(val nick: String, val time: String, val forum: String, val text: String, val iconUrl: String) : Item {
    fun getTextFromHtml(): Spanned = text.fromHtml()
}


