package cz.kutner.comicsdb.model

import java.util.*

data class ForumEntry(
    val nick: String,
    val time: Date,
    val forum: String,
    val text: String,
    val iconUrl: String
) : Item

