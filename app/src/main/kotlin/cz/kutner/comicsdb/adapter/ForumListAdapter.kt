package cz.kutner.comicsdb.adapter

import android.app.Activity
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.ForumListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class ForumListAdapter(activity: Activity, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ForumListAdapterDelegate(activity))
        setItems(items)
    }
}