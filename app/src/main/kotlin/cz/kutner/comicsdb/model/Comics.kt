package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml

data class Comics(val name: String, val id: Int, val published: String, val rating: Float) : Item {
    fun getNameFromHtml(): Spanned? = name.fromHtml()

}
