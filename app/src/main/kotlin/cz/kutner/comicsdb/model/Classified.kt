package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml


data class Classified(val nick: String, val time: String, val category: String, val text: String) : Item {
    var iconUrl: String? = null
    fun getTextFromHtml(): Spanned = text.fromHtml()
}


