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
import pl.aprilapps.switcher.Switcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class AbstractDetailActivity<Item: Any> : AppCompatActivity() {

    abstract val prefix: String
    abstract val extraName: String
    lateinit var result: Item
    val switcher: Switcher by lazy { Switcher.Builder(this).addContentView(content).addEmptyView(empty_view).addProgressView(progress_view).addErrorView(error_view).build() }
    val id: Int by lazy {
        val intent = intent
        var idTmp = 0
        if (Intent.ACTION_VIEW == intent.action) {
            //volá nás někdo přes URL
            try {
                val url = intent.dataString.replace("www.", "")
                idTmp = Integer.parseInt(url.removePrefix(prefix))
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
        try_again.setOnClickListener {
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
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
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

    fun runAsync(call: Call<Item>) {
        call.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>?, response: Response<Item>?) {
                if (response != null && response.isSuccessful) {
                    result = response.body()
                    showData()
                } else {
                    switcher.showErrorView()
                }
            }

            override fun onFailure(call: Call<Item>?, t: Throwable?) {
                switcher.showErrorView()
            }
        })
    }

    abstract fun loadData()

    abstract fun showData()
}
