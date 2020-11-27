package cz.kutner.comicsdb.model

import android.text.Spanned
import androidx.core.text.parseAsHtml
import java.util.*

data class AuthorDetail(
    val name: String,
    val country: String?,
    val id: Int,
    val comicses: ArrayList<Comics>,
    private val bio: String,
    private val notes: String,
    val photoUrl: String
) : Item {
    fun getNameFromHtml(): Spanned = name.parseAsHtml()
    fun getBioFromHtml(): Spanned = bio.parseAsHtml()
    fun getNotesFromHtml(): Spanned = notes.parseAsHtml()
}
