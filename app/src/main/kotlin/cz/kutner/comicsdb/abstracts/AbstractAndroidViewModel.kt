package cz.kutner.comicsdb.abstracts

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.Item
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

abstract class AbstractAndroidViewModel<Data : Item>(application: Application) : AndroidViewModel(application), KoinComponent {
    val retrofitModule by inject<RetrofitModule>()
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

    abstract fun getJob():Deferred<List<Data>?>

    fun loadNewData() = async(UI) {
        start = 0
        data.value = null
        loadData()
    }

    open fun loadData() = async(UI) {
        val newData = getJob().await()
        start++
        if (newData != null) {
            if (data.value == null) {
                data.value = newData
            } else {
                data.value = data.value?.plus(newData)
            }
        }
    }
}