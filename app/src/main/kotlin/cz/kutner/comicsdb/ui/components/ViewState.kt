package cz.kutner.comicsdb.ui.components

sealed class ViewState<out T> {
    data object Loading : ViewState<Nothing>()
    data class Content<T>(val data: T) : ViewState<T>()
    data class Error(val message: String? = null) : ViewState<Nothing>()
}
