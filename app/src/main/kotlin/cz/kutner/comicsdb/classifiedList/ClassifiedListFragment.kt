package cz.kutner.comicsdb.classifiedList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.kutner.comicsdb.abstracts.AbstractFragmentSpinner
import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.model.Filter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassifiedListFragment : AbstractFragmentSpinner<Classified>() {
    override val model: ClassifiedListViewModel by viewModel()
    override val adapter by lazy { ClassifiedListAdapter(layoutInflater, data) }

    init {
        spinnerValues = arrayOf(
            Filter(0, "Všechny inzeráty"),
            Filter(1, "Prodám"),
            Filter(2, "Koupím"),
            Filter(3, "Vyměním"),
            Filter(10, "Ostatní")
        )
    }

    override fun setupTitleAndSearchText() {
        (activity as AppCompatActivity).supportActionBar?.title = "Bazar"
    }

    companion object {
        fun newInstance(): ClassifiedListFragment {
            val args = Bundle()
            val fragment = ClassifiedListFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
