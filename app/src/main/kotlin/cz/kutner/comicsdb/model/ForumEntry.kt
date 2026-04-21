package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import java.util.Date

@Immutable
@Serializable
data class ForumEntry(
    val nick: String,
    @Serializable(with = DateSerializer::class)
    val time: Date,
    val forum: String,
    val text: String,
    val iconUrl: String
) : Item
