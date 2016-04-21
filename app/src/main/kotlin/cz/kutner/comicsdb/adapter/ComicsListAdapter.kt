package cz.kutner.comicsdb.adapter

import android.app.Activity
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.ComicsListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class ComicsListAdapter(activity:Activity, items: List<Item>): ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ComicsListAdapterDelegate(activity))
        setItems(items)
    }
}