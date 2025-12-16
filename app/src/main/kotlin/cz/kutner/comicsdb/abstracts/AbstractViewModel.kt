package cz.kutner.comicsdb.abstracts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.network.RetrofitModule
import kotlinx.coroutines.launch

abstract class AbstractViewModel<Data : Item>(val retrofitModule: RetrofitModule) :
    ViewModel() {
    private val data = MutableLiveData<Data>()
    fun getData(id: Int): LiveData<Data> {
        if (data.value == null) {
            loadData(id)
        }
        return data
    }

    abstract suspend fun getJob(id: Int): Data

    private fun loadData(id: Int) {
        viewModelScope.launch {
            val newData = getJob(id)
            data.postValue(newData)
        }
    }
}