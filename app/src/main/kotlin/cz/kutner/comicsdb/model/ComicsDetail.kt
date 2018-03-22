package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml
import java.util.*

data class ComicsDetail(
    val name: String,
    val id: Int,
    val published: String,
    val voteCount: Int,
    val rating: Float = 0.0f,
    val genre: String,
    val publisher: String,
    val issueNumber: String = "",
    val binding: String,
    val format: String,
    val pagesCount: String,
    val print: String,
    private val originalName: String,
    private val originalPublisher: String,
    val price: String,
    private val description: String,
    private val notes: String,
    val authors: ArrayList<Author>,
    val series: Series,
    val comments: ArrayList<Comment>,
    val cover: Image,
    val samples: ArrayList<Image>
) : Item {
    fun getNotesFromHtml(): Spanned = notes.fromHtml()
    fun getDescriptionFromHtml(): Spanned = description.fromHtml()
    fun getOriginals(): Spanned {
        var text = ""
        if (originalName != "") {
            text = "Původně: $originalName"
            if (originalPublisher != "") {
                text = "$text - $originalPublisher"
            }
        }
        return text.fromHtml()
    }
    fun getNameFromHtml(): Spanned? = name.fromHtml()
}
