package cz.kutner.comicsdb.adapter

import android.app.Activity
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.NewsListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class NewsListAdapter(activity: Activity, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(NewsListAdapterDelegate(activity))
        setItems(items)
    }
}