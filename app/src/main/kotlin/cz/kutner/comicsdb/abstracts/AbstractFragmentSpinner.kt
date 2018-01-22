package cz.kutner.comicsdb.abstracts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.crashlytics.android.Crashlytics
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.model.Item
import kotlinx.android.synthetic.main.fragment_list_spinner.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_progress.*
import pl.aprilapps.switcher.Switcher

abstract class AbstractFragmentSpinner<Data : Item> : AbstractFragment<Data>() {
    override val switcher: Switcher by lazy {
        Switcher.Builder(context!!).addContentView(content).addEmptyView(empty_view)
            .addProgressView(progress_view).addErrorView(error_view).build()
    }

    lateinit var spinnerValues: Array<Filter>
    var filter: Int = 0
    private var spinnerPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_list_spinner, container, false)

    override fun setupRecyclerView(view: View) {
        super.setupRecyclerView(view)
        val spinnerAdapter =
            ArrayAdapter(this.activity, android.R.layout.simple_spinner_item, spinnerValues)
        spinner.adapter = spinnerAdapter
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setSelection(spinnerPosition)
        spinner.onItemSelectedListener = ItemSelectedListener()
    }


    private inner class ItemSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            if (view == null) {
                Crashlytics.log("Parameters are parent : $parent, pos : $pos, id : $id")
                Crashlytics.log("Class is ${this.javaClass.canonicalName}")
                Crashlytics.logException(Exception("View is null"))
            }
            if (spinnerPosition != pos) {
                model.filterId = (spinner.selectedItem as Filter).id
                firstLoad = true
                switcher.showProgressView()
                model.loadNewData()
                spinnerPosition = pos
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }
}