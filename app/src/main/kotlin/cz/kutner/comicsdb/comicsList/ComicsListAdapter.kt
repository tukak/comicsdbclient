package cz.kutner.comicsdb.comicsList

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.comicsList.ComicsListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class ComicsListAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ComicsListAdapterDelegate(inflater))
        setItems(items)
    }
}