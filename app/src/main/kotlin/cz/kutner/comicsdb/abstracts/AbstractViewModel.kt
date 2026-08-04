package cz.kutner.comicsdb.abstracts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.ui.components.ViewState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class AbstractViewModel<Data : Item> : ViewModel() {
    private val _state = MutableStateFlow<ViewState<Data>>(ViewState.Loading)
    val state: StateFlow<ViewState<Data>> = _state.asStateFlow()

    fun loadData(id: Int) {
        _state.value = ViewState.Loading
        viewModelScope.launch {
            try {
                val newData = getJob(id)
                _state.value = ViewState.Content(newData)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = ViewState.Error(e.message)
            }
        }
    }

    abstract suspend fun getJob(id: Int): Data
}
