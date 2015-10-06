package cz.kutner.comicsdb.model

import java.util.*

public data class Series(public val name: String?, private val id: Int?, public val numberOfComicses: Int?) {
    private val notes: String? = null
    private val comicses: ArrayList<Comics>? = null
}
