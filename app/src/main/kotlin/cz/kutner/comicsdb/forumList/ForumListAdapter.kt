package cz.kutner.comicsdb.forumList

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.model.Item

class ForumListAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(ForumListAdapterDelegate(inflater))
        setItems(items)
    }
}