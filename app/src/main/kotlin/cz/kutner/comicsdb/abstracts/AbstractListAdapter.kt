package cz.kutner.comicsdb.abstracts

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import cz.kutner.comicsdb.model.Item

open class AbstractListAdapter : ListDelegationAdapter<List<Item>>()