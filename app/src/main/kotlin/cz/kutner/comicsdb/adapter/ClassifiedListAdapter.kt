package cz.kutner.comicsdb.adapter

import android.app.Activity
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.ClassifiedListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class ClassifiedListAdapter(activity: Activity, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ClassifiedListAdapterDelegate(activity))
        setItems(items)
    }
}