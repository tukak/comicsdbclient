package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class SeriesDetail(
    val name: String,
    val id: Int,
    val numberOfComicses: Int,
    val notes: String = "",
    val comicses: List<Comics>
) : Item
