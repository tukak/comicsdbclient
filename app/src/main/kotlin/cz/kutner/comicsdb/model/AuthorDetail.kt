package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml
import java.util.*

data class AuthorDetail(val name: String, val country: String?, val id: Int) : Item {
    var comicses: ArrayList<Comics> = ArrayList()
    private var bio: String? = null
    var notes: String? = null
    var photoUrl: String? = null

    fun getNameFromHtml(): Spanned? = name.fromHtml()

    fun getBioFromHtml(): Spanned? = bio?.fromHtml()

    fun getNotesFromHtml(): Spanned? = notes?.fromHtml()
}
