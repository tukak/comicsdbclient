package cz.kutner.comicsdb.model

import java.util.*

data class SeriesDetail(val name: String, val id: Int, var numberOfComicses: Int?): Item {
    val notes: String? = null
    val comicses: ArrayList<Comics> = ArrayList()
}
