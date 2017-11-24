package cz.kutner.comicsdb.newsList

import android.view.LayoutInflater
import cz.kutner.comicsdb.abstracts.AbstractListAdapter
import cz.kutner.comicsdb.model.Item

class NewsListAdapter(inflater: LayoutInflater, items: List<Item>) : AbstractListAdapter() {
    init {
        delegatesManager.addDelegate(NewsListAdapterDelegate(inflater))
        setItems(items)
    }
}