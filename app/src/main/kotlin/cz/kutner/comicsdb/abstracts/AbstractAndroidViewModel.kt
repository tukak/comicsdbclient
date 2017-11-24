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
import timber.log.Timber

abstract class AbstractAndroidViewModel<Data : Item>(application: Application) : AndroidViewModel(application), KoinComponent {
    val retrofitModule by inject<RetrofitModule>()
    var start = 0
    var count = 20
    lateinit var job: Deferred<List<Data>?>
    private val data = MutableLiveData<List<Data>>()
    var filterId = 0
    var searchText = ""
    fun getData(): LiveData<List<Data>> {
        Timber.i("Getting data")
        if (data.value == null) {
            loadData()
        }
        return data
    }

    fun loadNewData() = async(UI) {
        start = 0
        data.value = null
        loadData()
    }

    open fun loadData() = async(UI) {
        val newData = job.await()
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