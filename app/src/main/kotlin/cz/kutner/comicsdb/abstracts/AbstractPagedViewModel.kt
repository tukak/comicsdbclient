package cz.kutner.comicsdb.abstracts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.Item
import kotlinx.coroutines.*

abstract class AbstractPagedViewModel<Data : Item>(val retrofitModule: RetrofitModule) :
    ViewModel() {
    var start = 0
    var count = 20
    private val data = MutableLiveData<List<Data>>()
    var filterId = 0
    var searchText = ""

    fun getData(): LiveData<List<Data>> {
        if (data.value == null) {
            loadData()
        }
        return data
    }

    abstract fun getJob(): Deferred<List<Data>?>

    fun loadNewData() {
        start = 0
        data.value = null
        loadData()
    }


    fun loadData() = GlobalScope.async(Dispatchers.Main, CoroutineStart.DEFAULT) {
        val newData = getJob().await()
        start++
        if (newData != null) {
            if (data.value == null) {
                data.postValue(newData)
            } else {
                data.postValue(data.value?.plus(newData))
            }
        }
    }
}