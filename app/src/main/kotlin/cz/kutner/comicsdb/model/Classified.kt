package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import java.util.Date

@Immutable
@Serializable
data class Classified(
    val nick: String,
    @Serializable(with = DateSerializer::class)
    val time: Date,
    val category: String,
    val text: String,
    val iconUrl: String
) : Item {
    /** API returns category double-encoded (UTF-8 bytes read as Windows-1250). Reverse it. */
    val fixedCategory: String
        get() = try {
            String(category.toByteArray(charset("Windows-1250")), Charsets.UTF_8)
        } catch (_: Exception) {
            category
        }
}
