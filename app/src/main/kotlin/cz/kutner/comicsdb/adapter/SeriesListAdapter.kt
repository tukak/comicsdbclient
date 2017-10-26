package cz.kutner.comicsdb.adapter

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.SeriesListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class SeriesListAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(SeriesListAdapterDelegate(inflater))
        setItems(items)
    }
}