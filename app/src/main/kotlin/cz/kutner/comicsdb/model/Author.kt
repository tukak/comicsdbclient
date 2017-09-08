package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml

data class Author(val name: String, val country: String?, val id: Int) : Item {
    var role: String? = null

    fun getNameFromHtml(): Spanned? = name.fromHtml()

}
