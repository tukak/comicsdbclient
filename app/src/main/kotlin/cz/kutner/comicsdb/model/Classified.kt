package cz.kutner.comicsdb.model

import android.text.Spanned
import androidx.core.text.parseAsHtml
import java.util.*


data class Classified(
    val nick: String,
    val time: Date,
    val category: String,
    val text: String,
    val iconUrl: String
) : Item {
    fun getTextFromHtml(): Spanned = text.parseAsHtml()
}


