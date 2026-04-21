package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import java.util.Date

@Immutable
@Serializable
data class Comment(
    val nick: String,
    val stars: Int,
    val text: String,
    @Serializable(with = DateSerializer::class)
    val time: Date,
    val iconUrl: String
) : Item
