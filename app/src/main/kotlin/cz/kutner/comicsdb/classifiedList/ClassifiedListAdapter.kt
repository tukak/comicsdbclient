package cz.kutner.comicsdb.classifiedList

import android.view.LayoutInflater
import cz.kutner.comicsdb.abstracts.AbstractListAdapter
import cz.kutner.comicsdb.model.Item

class ClassifiedListAdapter(inflater: LayoutInflater, items: List<Item>) : AbstractListAdapter() {
    init {
        delegatesManager.addDelegate(ClassifiedListAdapterDelegate(inflater))
        setItems(items)
    }
}