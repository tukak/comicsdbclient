package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import java.util.Date

@Immutable
@Serializable
data class NewsItem(
    val title: String? = null,
    val nick: String,
    val text: String,
    @Serializable(with = DateSerializer::class)
    val time: Date
) : Item {
    fun getTextWithUrl(): String =
        text.replace("href='/", "href='http://www.comicsdb.cz/")
}
