package cz.kutner.comicsdb.abstracts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.ui.components.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class AbstractPagedViewModel<Data : Item>(val retrofitModule: RetrofitModule) :
    ViewModel() {
    protected var start = 0
    protected var count = 20
    var filterId = 0
    var searchText = ""

    private val _state = MutableStateFlow<ViewState<List<Data>>>(ViewState.Loading)
    val state: StateFlow<ViewState<List<Data>>> = _state.asStateFlow()

    abstract suspend fun getJob(): List<Data>

    fun loadNewData() {
        start = 0
        isLoading = false
        _state.value = ViewState.Loading
        loadData()
    }

    private var isLoading = false

    fun loadData() {
        if (isLoading) return
        isLoading = true
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
            } catch (e: Exception) {
                if ((_state.value as? ViewState.Content)?.data == null) {
                    _state.value = ViewState.Error(e.message)
                }
            } finally {
                isLoading = false
            }
        }
    }
}
