package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Filter
import kotlinx.android.synthetic.main.fragment_list_spinner.*

abstract class AbstractFragmentSpinner<Item : Any> : AbstractFragment<Item>() {
    lateinit var spinnerValues: Array<Filter>
    var filter: Int
    private var spinnerPosition: Int = 0

    init {
        lastPage = 0
        loading = false
        filter = 0
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_list_spinner, container, false)
    }

    override fun showData() {
        if (activity?.isFinishing == false) {
            searchRunning = false
            if (firstLoad) {
                val spinnerAdapter = ArrayAdapter(this.activity, android.R.layout.simple_spinner_item, spinnerValues)
                spinner.adapter = spinnerAdapter
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.setSelection(spinnerPosition)
                spinner.onItemSelectedListener = itemSelectedListener()

                switcher.showContentView()
                firstLoad = false
            }
            if (result.isNotEmpty()) {
                if (lastItem == null || lastItem != result[0]) {
                    lastItem = result[0]
                    data.addAll(result)
                }
            }
            adapter.notifyDataSetChanged()
            loading = false
        }
    }

    private inner class itemSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            if (!spinnerPosition.equals(pos)) {
                filter = (spinner.selectedItem as Filter).id
                data.clear()
                lastItem = null
                switcher.showProgressView()
                firstLoad = true
                lastPage = 0
                spinnerPosition = pos
                loadData()
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }
}