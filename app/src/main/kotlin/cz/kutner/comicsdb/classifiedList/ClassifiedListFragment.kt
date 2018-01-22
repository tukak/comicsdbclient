package cz.kutner.comicsdb.classifiedList

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.kutner.comicsdb.abstracts.AbstractFragmentSpinner
import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.utils.logVisit

class ClassifiedListFragment : AbstractFragmentSpinner<Classified>() {
    init {
        spinnerValues = arrayOf(
            Filter(0, "Všechny inzeráty"),
            Filter(1, "Prodám"),
            Filter(2, "Koupím"),
            Filter(3, "Vyměním"),
            Filter(10, "Ostatní")
        )
    }

    override fun setupRecyclerView(view: View) {
        model = ViewModelProviders.of(this).get(ClassifiedListViewModel::class.java)
        adapter = ClassifiedListAdapter(layoutInflater, data)
        super.setupRecyclerView(view)
    }

    override fun setupTitleAndSearchText() {
        (activity as AppCompatActivity).supportActionBar?.title = "Bazar"
        firebase.logVisit(contentName = "Zobrazení inzerátů", contentType = "Inzerát")
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
