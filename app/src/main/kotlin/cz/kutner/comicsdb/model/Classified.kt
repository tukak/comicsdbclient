package cz.kutner.comicsdb.model

import java.util.*


data class Classified(
    val nick: String,
    val time: Date,
    val category: String,
    val text: String,
    val iconUrl: String
) : Item


