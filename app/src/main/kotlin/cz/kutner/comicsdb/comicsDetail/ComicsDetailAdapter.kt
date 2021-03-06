package cz.kutner.comicsdb.comicsDetail

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import cz.kutner.comicsdb.model.Item

class ComicsDetailAdapter(inflater: LayoutInflater, items: List<Item>) :
    ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ComicsDetailAdapterDelegate(inflater))
            .addDelegate(CommentsAdapterDelegate(inflater))
        setItems(items)
    }
}