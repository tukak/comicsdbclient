package cz.kutner.comicsdb.model

import android.text.Spanned
import cz.kutner.comicsdb.utils.fromHtml
import java.util.*

data class ComicsDetail(var name: String, var id: Int): Item {
    var published: String? = ""
    var voteCount: Int? = null
    var rating: Int = 0
    var genre: String? = ""
    var publisher: String? = ""
    var issn: String? = ""
    var issueNumber: String? = ""
    var binding: String? = ""
    var format: String? = ""
    var pagesCount: String? = ""
    var print: String? = ""
    var originalName: String? = ""
    var originalPublisher: String? = ""
    var price: String? = ""
    var description: String = ""
    var notes: String = ""
    var authors: ArrayList<Author> = ArrayList()
    var series: Series? = null
    var comments: ArrayList<Comment> = ArrayList()
        private set
    var cover: Image? = null
    var samples: ArrayList<Image> = ArrayList()

    fun getNotesFromHtml(): Spanned = notes.fromHtml()
    fun getDescriptionFromHtml(): Spanned = description.fromHtml()
    fun getStars(): Float = Math.round(rating.toFloat() / 20).toFloat()
    fun getOriginals(): String {
        var text: String = ""
        if (originalName != "") {
            text = "Původně: $originalName"
            if (originalPublisher != "") {
                text = "$text - $originalPublisher"
            }
        }
        return text
    }

}
