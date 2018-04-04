package cz.kutner.comicsdb.model

import android.text.Spanned
import androidx.core.text.parseAsHtml
import java.util.*

data class Comment(
    val nick: String,
    val stars: Int,
    val text: String,
    val time: Date,
    val iconUrl: String
) : Item {
    fun getTextFromHtml(): Spanned = text.parseAsHtml()
}
