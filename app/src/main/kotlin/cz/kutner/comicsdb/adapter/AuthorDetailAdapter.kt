package cz.kutner.comicsdb.adapter

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.AuthorAdapterDelegate
import cz.kutner.comicsdb.adapter.delegate.ComicsListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class AuthorDetailAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(AuthorAdapterDelegate(inflater))
                .addDelegate(ComicsListAdapterDelegate(inflater))
        setItems(items)
    }
}