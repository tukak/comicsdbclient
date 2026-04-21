package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Series(val name: String = "", val id: Int = 0, val numberOfComicses: Int = 0) : Item
