package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.perf.metrics.AddTrace
import cz.kutner.comicsdb.adapter.ClassifiedListAdapter
import cz.kutner.comicsdb.connector.helper.ClassifiedHelper
import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.utils.logVisit

class ClassifiedFragment : AbstractFragment<Classified>() {

    init {
        preloadCount = 8
        spinnerEnabled = true
        spinnerValues = arrayOf("Všechny inzeráty", "Prodám", "Koupím", "Vyměním", "Ostatní")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ClassifiedListAdapter(activity, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @AddTrace(name = "ClassifiedLoadData")
    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val call = retrofitModule.classifiedService.filteredClassifiedList(lastPage, ClassifiedHelper.getCategoryId(filter))
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
