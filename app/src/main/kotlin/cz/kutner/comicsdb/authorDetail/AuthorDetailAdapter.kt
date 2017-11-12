package cz.kutner.comicsdb.authorDetail

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.adapter.delegate.ComicsListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class AuthorDetailAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(AuthorDetailAdapterDelegate(inflater))
                .addDelegate(ComicsListAdapterDelegate(inflater))
        setItems(items)
    }
}