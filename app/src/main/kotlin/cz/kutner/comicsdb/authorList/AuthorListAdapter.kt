package cz.kutner.comicsdb.authorList

import android.view.LayoutInflater
import cz.kutner.comicsdb.abstracts.AbstractListAdapter
import cz.kutner.comicsdb.model.Item

class AuthorListAdapter(inflater: LayoutInflater, items: List<Item>) : AbstractListAdapter() {
    init {
        delegatesManager.addDelegate(AuthorListAdapterDelegate(inflater))
        setItems(items)
    }
}