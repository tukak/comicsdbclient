package cz.kutner.comicsdb.model

import android.text.Spanned
import androidx.core.text.parseAsHtml

data class Comics(val name: String, val id: Int, val published: String, val rating: Float) : Item {
    fun getNameFromHtml(): Spanned = name.parseAsHtml()

}
