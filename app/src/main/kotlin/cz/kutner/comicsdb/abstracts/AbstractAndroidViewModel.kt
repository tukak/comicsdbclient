package cz.kutner.comicsdb.abstracts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Item
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

abstract class AbstractAndroidViewModel<Data : Item>(val retrofitModule: RetrofitModule) :
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


    fun loadData() = async(UI) {
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