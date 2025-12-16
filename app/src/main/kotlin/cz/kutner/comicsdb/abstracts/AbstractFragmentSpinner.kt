package cz.kutner.comicsdb.abstracts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.kutner.comicsdb.databinding.FragmentListSpinnerBinding
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.model.Item
import cz.kutner.comicsdb.utils.ViewStateSwitcher

abstract class AbstractFragmentSpinner<Data : Item> : AbstractFragment<Data>() {
    private var _spinnerBinding: FragmentListSpinnerBinding? = null
    private val spinnerBinding get() = _spinnerBinding!!

    override val recyclerView: RecyclerView get() = spinnerBinding.recyclerView

    lateinit var spinnerValues: Array<Filter>
    private var spinnerPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _spinnerBinding = FragmentListSpinnerBinding.inflate(inflater, container, false)
        return spinnerBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _spinnerBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        switcher = ViewStateSwitcher.Builder(requireContext())
            .addContentView(spinnerBinding.content)
            .addEmptyView(spinnerBinding.viewEmptyInclude.root)
            .addProgressView(spinnerBinding.viewProgressInclude.root)
            .addErrorView(spinnerBinding.viewErrorInclude.root)
            .build()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupTryAgainButton() {
        spinnerBinding.viewErrorInclude.tryAgain.setOnClickListener {
            if (networkModule.isConnected()) {
                checkConnectionAndLoadData()
            }
        }
    }

    override fun setupRecyclerView(view: View) {
        super.setupRecyclerView(view)
        val context = this.context
        if (context != null) {
            val spinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerValues)
            spinnerBinding.spinner.adapter = spinnerAdapter
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBinding.spinner.setSelection(spinnerPosition)
            spinnerBinding.spinner.onItemSelectedListener = ItemSelectedListener()
        }
    }


    private inner class ItemSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            if (spinnerPosition != pos) {
                model.filterId = (spinnerBinding.spinner.selectedItem as Filter).id
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