package cz.kutner.comicsdb.seriesList

import android.view.LayoutInflater
import cz.kutner.comicsdb.abstracts.AbstractListAdapter
import cz.kutner.comicsdb.model.Item

class SeriesListAdapter(inflater: LayoutInflater, items: List<Item>) : AbstractListAdapter() {
    init {
        delegatesManager.addDelegate(SeriesListAdapterDelegate(inflater))
        setItems(items)
    }
}