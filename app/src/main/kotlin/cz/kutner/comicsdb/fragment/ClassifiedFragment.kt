package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.adapter.ClassifiedListAdapter
import cz.kutner.comicsdb.connector.helper.ClassifiedHelper
import cz.kutner.comicsdb.connector.service.ClassifiedService
import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.utils.Utils
import javax.inject.Inject

class ClassifiedFragment : AbstractFragment<Classified>() {

    @Inject lateinit var classifiedService: ClassifiedService

    init {
        preloadCount = 8
        spinnerEnabled = true
        spinnerValues = arrayOf("Všechny inzeráty", "Prodám", "Koupím", "Vyměním", "Ostatní")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as ComicsDBApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ClassifiedListAdapter(activity, data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val searchText = ""
            val call = classifiedService.filteredClassifiedList(lastPage, ClassifiedHelper.getCategoryId(filter), searchText)
            runAsync(call)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Bazar"
        Utils.logVisit(contentName = "Zobrazení inzerátů", contentType = "Inzerát")
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
