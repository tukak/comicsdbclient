package cz.kutner.comicsdb.adapter

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.ComicsListAdapterDelegate
import cz.kutner.comicsdb.adapter.delegate.SeriesAdapterDelegate
import cz.kutner.comicsdb.model.Item

class SeriesDetailAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(SeriesAdapterDelegate(inflater))
                .addDelegate(ComicsListAdapterDelegate(inflater))
        setItems(items)
    }
}