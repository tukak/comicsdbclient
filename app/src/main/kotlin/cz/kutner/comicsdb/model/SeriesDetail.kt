package cz.kutner.comicsdb.model

import java.util.*

data class SeriesDetail(
    val name: String,
    val id: Int,
    val numberOfComicses: Int,
    val notes: String,
    val comicses: ArrayList<Comics>
) : Item
