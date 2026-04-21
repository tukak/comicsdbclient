package cz.kutner.comicsdb.model

import android.text.Spanned
import androidx.compose.runtime.Immutable
import androidx.core.text.parseAsHtml
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class AuthorDetail(
    val name: String,
    val country: String? = null,
    val id: Int,
    val comicses: List<Comics>,
    private val bio: String,
    private val notes: String,
    val photoUrl: String
) : Item {
    fun getBioFromHtml(): Spanned = bio.parseAsHtml()
    fun getNotesFromHtml(): Spanned = notes.parseAsHtml()
}
