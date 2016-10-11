package cz.kutner.comicsdb.adapter

import android.app.Activity
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.AuthorListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class AuthorListAdapter(activity: Activity, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(AuthorListAdapterDelegate(activity))
        setItems(items)
    }
}