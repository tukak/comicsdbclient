package cz.kutner.comicsdb.adapter.delegate

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import cz.kutner.comicsdb.databinding.ListItemClassifiedBinding
import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.model.Item


class ClassifiedListAdapterDelegate(activity: Activity) : AdapterDelegate<List<Item>>() {
    private val inflater: LayoutInflater = activity.layoutInflater

    override fun isForViewType(items: List<Item>, position: Int): Boolean {
        return items[position] is Classified
    }

    override fun onBindViewHolder(items: List<Item>, position: Int, holder: RecyclerView.ViewHolder, payloads: MutableList<Any>) {
        val vh: ClassifiedViewHolder = holder as ClassifiedViewHolder
        val classified: Classified = items[position] as Classified

        vh.bind(classified)
    }

    override fun onCreateViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val classifiedBinding = ListItemClassifiedBinding.inflate(inflater, parent, false)
        return ClassifiedViewHolder(classifiedBinding)
    }

    internal class ClassifiedViewHolder(val binding: ListItemClassifiedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(classified: Classified) {
            binding.classified = classified
            binding.executePendingBindings()
        }
    }

}