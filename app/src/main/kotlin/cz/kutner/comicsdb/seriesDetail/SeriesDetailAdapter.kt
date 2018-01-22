package cz.kutner.comicsdb.seriesDetail

import android.view.LayoutInflater
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import cz.kutner.comicsdb.comicsList.ComicsListAdapterDelegate
import cz.kutner.comicsdb.model.Item

class SeriesDetailAdapter(inflater: LayoutInflater, items: List<Item>) :
    ListDelegationAdapter<List<Item>>() {
    init {
        delegatesManager.addDelegate(SeriesDetailAdapterDelegate(inflater))
            .addDelegate(ComicsListAdapterDelegate(inflater))
        setItems(items)
    }
}