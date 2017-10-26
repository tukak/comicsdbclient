package cz.kutner.comicsdb.adapter

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.NewsListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class NewsListAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(NewsListAdapterDelegate(inflater))
        setItems(items)
    }
}