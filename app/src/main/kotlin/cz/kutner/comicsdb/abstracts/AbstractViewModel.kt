package cz.kutner.comicsdb.abstracts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.network.RetrofitModule
import kotlinx.coroutines.*

abstract class AbstractViewModel<Data : Item>(val retrofitModule: RetrofitModule) :
    ViewModel() {
    private val data = MutableLiveData<Data>()
    fun getData(id: Int): LiveData<Data> {
        if (data.value == null) {
            loadData(id)
        }
        return data
    }

    abstract fun getJob(id: Int): Deferred<Data>

    private fun loadData(id: Int) = GlobalScope.async(Dispatchers.Main, CoroutineStart.DEFAULT) {
        val newData = getJob(id).await()
        data.postValue(newData)
    }
}