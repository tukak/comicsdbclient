package cz.kutner.comicsdb.adapter

import android.app.Activity
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.ComicsListAdapterDelegate
import cz.kutner.comicsdb.adapter.delegate.SeriesAdapterDelegate
import cz.kutner.comicsdb.model.Item

class SeriesDetailAdapter(activity:Activity, items: List<Item>): ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(SeriesAdapterDelegate(activity))
                        .addDelegate(ComicsListAdapterDelegate(activity))
        setItems(items)
    }
}