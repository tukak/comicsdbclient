package cz.kutner.comicsdb.utils

import android.support.v7.util.DiffUtil
import cz.kutner.comicsdb.model.Item

class ItemDiffCallback(private val oldList: List<Item>, private val newList: List<Item>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        areItemsTheSame(oldItemPosition, newItemPosition)
}