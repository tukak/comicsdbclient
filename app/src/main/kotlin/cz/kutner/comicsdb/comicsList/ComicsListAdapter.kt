package cz.kutner.comicsdb.comicsList

import android.view.LayoutInflater
import cz.kutner.comicsdb.abstracts.AbstractListAdapter
import cz.kutner.comicsdb.model.Item

class ComicsListAdapter(inflater: LayoutInflater, items: List<Item>) : AbstractListAdapter() {
    init {
        delegatesManager.addDelegate(ComicsListAdapterDelegate(inflater))
        setItems(items)
    }
}