package cz.kutner.comicsdb.abstracts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.main.MainActivity
import cz.kutner.comicsdb.di.NetworkModule
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_progress.*
import org.koin.android.ext.android.inject
import pl.aprilapps.switcher.Switcher

abstract class AbstractDetailActivity<Item : Any> : AppCompatActivity() {

    lateinit var result: Item
    val switcher: Switcher by lazy { Switcher.Builder(this).addContentView(content).addEmptyView(empty_view).addProgressView(progress_view).addErrorView(error_view).build() }
    val firebase by inject<FirebaseAnalytics>()
    private val networkModule by inject<NetworkModule>()

    val id: Int by lazy {
        if (Intent.ACTION_VIEW == intent.action) {
            val uri = Uri.parse(intent.dataString)
            Integer.parseInt(uri.pathSegments[1])
        } else {
            intent.getIntExtra(Intent.EXTRA_UID, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupToolbar()

        val llm = LinearLayoutManager(this)
        try_again.setOnClickListener {
            if (networkModule.isConnected()) {
                onResume()
            }
        }

        recycler_view.layoutManager = llm
        switcher.showProgressView()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //searchView.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (!networkModule.isConnected()) {
            switcher.showErrorView()
        } else {
            switcher.showProgressView()
            loadData()
        }
    }

    abstract fun loadData()
}
