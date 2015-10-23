package cz.kutner.comicsdb.model

import java.util.*

public data class Author(public val name: String?, public val country: String?, private val id: Int?) {
    private val comicses: ArrayList<Comics>? = null
}
