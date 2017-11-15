package cz.kutner.comicsdb.seriesList

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.model.Item

class SeriesListAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(SeriesListAdapterDelegate(inflater))
        setItems(items)
    }
}