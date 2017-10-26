package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.adapter.ClassifiedListAdapter
import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.utils.logVisit

class ClassifiedFragment : AbstractFragmentSpinner<Classified>() {

    init {
        spinnerValues = arrayOf(Filter(0, "Všechny inzeráty"),
                Filter(1, "Prodám"),
                Filter(2, "Koupím"),
                Filter(3, "Vyměním"),
                Filter(10, "Ostatní"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ClassifiedListAdapter(inflater, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val call = retrofitModule.classifiedService.filteredClassifiedList(lastPage * preloadCount, preloadCount, filter)
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Bazar"
        firebase.logVisit(contentName = "Zobrazení inzerátů", contentType = "Inzerát")
    }

    companion object {

        fun newInstance(): ClassifiedFragment {

            val args = Bundle()

            val fragment = ClassifiedFragment()
            fragment.arguments = args
            return fragment
        }
    }


}
