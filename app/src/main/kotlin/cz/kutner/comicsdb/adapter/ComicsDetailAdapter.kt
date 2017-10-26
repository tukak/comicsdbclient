package cz.kutner.comicsdb.adapter

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.ComicsAdapterDelegate
import cz.kutner.comicsdb.adapter.delegate.CommentsAdapterDelegate
import cz.kutner.comicsdb.model.Item

class ComicsDetailAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ComicsAdapterDelegate(inflater))
                .addDelegate(CommentsAdapterDelegate(inflater))
        setItems(items)
    }
}