package cz.kutner.comicsdb.adapter

import android.app.Activity
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.AuthorAdapterDelegate
import cz.kutner.comicsdb.adapter.delegate.ComicsListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class AuthorDetailAdapter(activity:Activity, items: List<Item>): ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(AuthorAdapterDelegate(activity))
                        .addDelegate(ComicsListAdapterDelegate(activity))
        setItems(items)
    }
}