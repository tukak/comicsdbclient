package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml

data class Author(val name: String, val country: String?, val id: Int, val role: String) : Item {
    fun getNameFromHtml(): Spanned? = name.fromHtml()
}
