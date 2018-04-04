package cz.kutner.comicsdb.model

import android.text.Spanned
import androidx.core.text.parseAsHtml

data class Author(val name: String, val country: String?, val id: Int, val role: String) : Item {
    fun getNameFromHtml(): Spanned? = name.parseAsHtml()
}
