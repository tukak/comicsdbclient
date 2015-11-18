package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.Utils
import kotlinx.android.synthetic.fragment.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.onClick
import pl.aprilapps.switcher.Switcher
import uk.co.ribot.easyadapter.EasyRecyclerAdapter
import java.util.*

public abstract class AbstractFragment<Item : Any> : Fragment(), AnkoLogger {
    var lastPage: Int = 0
    private var firstLoad: Boolean = false
    var searchRunning: Boolean = false
    private var loading: Boolean = false
    private var lastItem: Item? = null
    var data: MutableList<Item> = ArrayList()
    var result: List<Item> = ArrayList()
    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    var adapter: EasyRecyclerAdapter<Any>? = null
    var preloadCount: Int = 0
    var endless: Boolean = false
    var spinnerEnabled: Boolean = false
    var spinnerValues: Array<String>? = null
    var filter: String
    private var spinnerPosition: Int? = null
    private var switcher: Switcher? = null

    init {
        lastPage = 1
        loading = false
        endless = true
        spinnerEnabled = false
        filter = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switcher = Switcher.Builder().withContentView(content).withEmptyView(empty_view).withProgressView(progress_view).withErrorView(error_view).build()
        val llm = LinearLayoutManager(view?.context)
        recycler_view.layoutManager = llm
        recycler_view.adapter = adapter
        try_again.onClick {
            if (Utils.isConnected()) {
                checkConnectionAndLoadData();
            }
        }

        if (endless) {
            recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    visibleItemCount = llm.childCount
                    totalItemCount = llm.itemCount
                    pastVisibleItems = llm.findFirstVisibleItemPosition()
                    if (!loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount - preloadCount) {
                            loading = true
                            loadData()
                        }
                    }
                }
            })
        }
        switcher?.showProgressView()
    }

    abstract fun loadData()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkConnectionAndLoadData()
    }

    private fun checkConnectionAndLoadData() {
        if (!Utils.isConnected()) {
            switcher?.showErrorView()
        } else {
            switcher?.showProgressView()
            if (!result.isEmpty()) {
                showData()
            } else {
                firstLoad = true
                loadData()
            }

        }
    }

    public fun showData() {
        info(firstLoad)
        if (activity?.isFinishing == false) {
            searchRunning = false
            if (firstLoad) {
                if (!spinnerEnabled && spinner != null) {
                    spinner?.visibility = View.GONE
                    filter_text.visibility = View.GONE
                }
                if (spinnerEnabled && spinner != null) {
                    val spinnerAdapter = ArrayAdapter(this.activity, android.R.layout.simple_spinner_item, spinnerValues)
                    spinner?.adapter = spinnerAdapter
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    if (spinnerPosition != null) {
                        spinner?.setSelection(spinnerPosition!!)
                    } else {
                        spinnerPosition = 0
                    }
                    spinner?.onItemSelectedListener = itemSelectedListener()
                }
                switcher?.showContentView()
                firstLoad = false
            }
            info(adapter)
            info(data.size)
            if (result.size > 0) {
                if (lastItem == null || lastItem != result[0]) {
                    lastItem = result[0]
                    if (!endless) {
                        data.clear()
                    }
                    data.addAll(result)
                }
            }
            adapter?.notifyDataSetChanged()
            loading = false
        }
    }

    private inner class itemSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            if (spinnerPosition !== pos) {
                filter = spinner?.selectedItem.toString()
                data.clear()
                lastItem = null
                switcher?.showProgressView()
                firstLoad = true
                lastPage = 1
                spinnerPosition = pos
                loadData()
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }
}