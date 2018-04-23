package cz.kutner.comicsdb.model

import java.util.*

data class Comment(
    val nick: String,
    val stars: Int,
    val text: String,
    val time: Date,
    val iconUrl: String
) : Item