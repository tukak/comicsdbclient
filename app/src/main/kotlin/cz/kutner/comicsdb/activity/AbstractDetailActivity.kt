package cz.kutner.comicsdb.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.utils.Utils
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_progress.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.singleTop
import pl.aprilapps.switcher.Switcher

abstract class AbstractDetailActivity : AppCompatActivity() {

    abstract val prefix: String
    abstract val extraName: String
    val switcher: Switcher by lazy { Switcher.Builder(this).addContentView(content).addEmptyView(empty_view).addProgressView(progress_view).addErrorView(error_view).build() }
    val id: Int by lazy {
        val intent = intent
        var idTmp = 0
        if (Intent.ACTION_VIEW == intent.action) {
            //volá nás někdo přes URL
            try {
                idTmp = Integer.parseInt(intent.dataString.removePrefix(prefix))
            } catch (e: Exception) {
            }

        } else {
            idTmp = intent.getIntExtra(extraName, 0)
        }
        idTmp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupToolbar()

        val llm = LinearLayoutManager(this)
        try_again.onClick {
            if (Utils.isConnected()) {
                onResume()
            }
        }

        recycler_view.layoutManager = llm
        spinner.visibility = View.GONE
        filter_text.visibility = View.GONE
        switcher.showProgressView()
    }

    fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //searchView.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            startActivity(intentFor<MainActivity>().singleTop().clearTop())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (!Utils.isConnected()) {
            switcher.showErrorView()
        } else {
            switcher.showProgressView()
            loadData()
        }
    }

    abstract fun loadData()

    abstract fun showData()
}
