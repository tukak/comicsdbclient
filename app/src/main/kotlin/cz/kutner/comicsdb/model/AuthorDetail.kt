package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml
import java.util.*

data class AuthorDetail(val name: String, val country: String?, val id: Int, val comicses: ArrayList<Comics>, private val bio: String, private val notes: String, val photoUrl: String) : Item {
    fun getNameFromHtml(): Spanned? = name.fromHtml()
    fun getBioFromHtml(): Spanned? = bio.fromHtml()
    fun getNotesFromHtml(): Spanned? = notes.fromHtml()
}
