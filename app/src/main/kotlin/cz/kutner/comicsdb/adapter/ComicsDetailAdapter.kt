package cz.kutner.comicsdb.adapter

import android.app.Activity
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.ComicsAdapterDelegate
import cz.kutner.comicsdb.adapter.delegate.CommentsAdapterDelegate
import cz.kutner.comicsdb.model.Item

class ComicsDetailAdapter(activity: Activity, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ComicsAdapterDelegate(activity))
                .addDelegate(CommentsAdapterDelegate(activity))
        setItems(items)
    }
}