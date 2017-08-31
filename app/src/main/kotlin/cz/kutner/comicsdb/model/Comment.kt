package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml

data class Comment(val nick: String, val stars: Int, val text: String, val time: String): Item {
    var iconUrl: String? = null
    fun getTextFromHtml(): Spanned = text.fromHtml()


}
