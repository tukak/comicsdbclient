package cz.kutner.comicsdb.adapter

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.AuthorListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class AuthorListAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(AuthorListAdapterDelegate(inflater))
        setItems(items)
    }
}