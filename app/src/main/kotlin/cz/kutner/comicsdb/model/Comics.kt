package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Comics(val name: String, val id: Int, val published: String, val rating: Float) : Item
