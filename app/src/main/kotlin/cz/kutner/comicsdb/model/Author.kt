package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Author(val name: String, val country: String? = null, val id: Int, val role: String = "") : Item
