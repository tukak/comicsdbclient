package cz.kutner.comicsdb.model

import java.util.*

public data class Series(public val name: String, val id: Int, public var numberOfComicses: Int?) {
    val notes: String? = null
    val comicses: ArrayList<Comics> = ArrayList()
}
