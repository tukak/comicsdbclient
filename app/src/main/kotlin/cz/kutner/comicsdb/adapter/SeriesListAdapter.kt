package cz.kutner.comicsdb.adapter

import android.app.Activity
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.SeriesListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class SeriesListAdapter(activity:Activity, items: List<Item>): ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(SeriesListAdapterDelegate(activity))
        setItems(items)
    }
}