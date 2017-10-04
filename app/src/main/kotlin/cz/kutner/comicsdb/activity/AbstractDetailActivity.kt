package cz.kutner.comicsdb.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import co.metalab.asyncawait.RetrofitHttpError
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.di.NetworkModule
import cz.kutner.comicsdb.di.RetrofitModule
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_progress.*
import org.koin.android.ext.android.app.inject
import pl.aprilapps.switcher.Switcher
import retrofit2.Call
import timber.log.Timber

abstract class AbstractDetailActivity<Item : Any> : AppCompatActivity() {

    lateinit var result: Item
    val switcher: Switcher by lazy { Switcher.Builder(this).addContentView(content).addEmptyView(empty_view).addProgressView(progress_view).addErrorView(error_view).build() }
    val retrofitModule by inject<RetrofitModule>()
    val firebase by inject<FirebaseAnalytics>()
    private val networkModule by inject<NetworkModule>()

    val id: Int by lazy {
        if (Intent.ACTION_VIEW == intent.action) {
            Integer.parseInt(intent.dataString.split("/")[4])
        } else {
            intent.getIntExtra(MainActivity.ITEM_ID, 0)
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

    fun runAsync(call: Call<Item>) {
        async {
            try {
                result = awaitSuccessful(call)
            } catch (e: RetrofitHttpError) {
                switcher.showErrorView()
                Timber.e(e)
            }
            showData()
        }
    }

    abstract fun loadData()

    abstract fun showData()
}
