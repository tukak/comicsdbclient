package cz.kutner.comicsdb.model

import androidx.compose.runtime.Immutable

@Immutable
data class Filter(val id: Int, val title: String) {
    override fun toString(): String = this.title
}
