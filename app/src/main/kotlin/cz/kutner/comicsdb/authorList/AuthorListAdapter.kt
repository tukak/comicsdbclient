package cz.kutner.comicsdb.authorList

import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.utils.ItemDiffCallback

class AuthorListAdapter(inflater: LayoutInflater, items: List<Item>) : ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(AuthorListAdapterDelegate(inflater))
        setItems(items)
    }

    fun updateList(oldList: List<Item>) {
        val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(oldList, this.items))
        diffResult.dispatchUpdatesTo(this)
    }
}