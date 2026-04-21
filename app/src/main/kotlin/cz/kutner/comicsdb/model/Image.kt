package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Image(val previewUrl: String, val fullUrl: String, val caption: String)
