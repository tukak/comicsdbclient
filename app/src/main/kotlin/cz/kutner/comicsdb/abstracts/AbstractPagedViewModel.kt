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

abstract class AbstractPagedViewModel<Data : Item> : ViewModel() {
    protected var start = 0
    protected val count = 20
    var filterId = 0
    var searchText = ""

    private var isLoading = false

    private val _state = MutableStateFlow<ViewState<List<Data>>>(ViewState.Loading)
    val state: StateFlow<ViewState<List<Data>>> = _state.asStateFlow()

    private val _pageLoadFailed = MutableStateFlow(false)
    val pageLoadFailed: StateFlow<Boolean> = _pageLoadFailed.asStateFlow()

    abstract suspend fun getJob(): List<Data>

    fun loadNewData() {
        start = 0
        isLoading = false
        _pageLoadFailed.value = false
        _state.value = ViewState.Loading
        loadData()
    }

    fun loadData() {
        if (isLoading) return
        isLoading = true
        _pageLoadFailed.value = false
        viewModelScope.launch {
            try {
                val newData = getJob()
                start++
                val currentData = (_state.value as? ViewState.Content)?.data
                if (currentData == null) {
                    _state.value = ViewState.Content(newData)
                } else {
                    _state.value = ViewState.Content(currentData + newData)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                if ((_state.value as? ViewState.Content)?.data == null) {
                    _state.value = ViewState.Error(e.message)
                } else {
                    _pageLoadFailed.value = true
                }
            } finally {
                isLoading = false
            }
        }
    }
}
