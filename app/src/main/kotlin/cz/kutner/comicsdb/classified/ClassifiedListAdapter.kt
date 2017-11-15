package cz.kutner.comicsdb.classified

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.model.Item

class ClassifiedListAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ClassifiedListAdapterDelegate(inflater))
        setItems(items)
    }
}