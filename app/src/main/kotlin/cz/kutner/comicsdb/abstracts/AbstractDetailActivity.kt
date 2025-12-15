package cz.kutner.comicsdb.abstracts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.MenuItem
import androidx.lifecycle.Observer
import cz.kutner.comicsdb.databinding.ActivityDetailBinding
import cz.kutner.comicsdb.network.NetworkModule
import cz.kutner.comicsdb.main.MainActivity
import cz.kutner.comicsdb.model.Item
import org.koin.android.ext.android.inject
import pl.aprilapps.switcher.Switcher
import timber.log.Timber

abstract class AbstractDetailActivity<Data : Item> : AppCompatActivity() {
    abstract val model: AbstractViewModel<Data>

    protected lateinit var binding: ActivityDetailBinding

    private val switcher: Switcher by lazy {
        Switcher.Builder(this).addContentView(binding.fragmentListInclude.content).addEmptyView(binding.fragmentListInclude.viewEmptyInclude.root)
            .addProgressView(binding.fragmentListInclude.viewProgressInclude.root).addErrorView(binding.fragmentListInclude.viewErrorInclude.root).build()
    }
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
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()

        val llm = LinearLayoutManager(this)
        binding.fragmentListInclude.viewErrorInclude.tryAgain.setOnClickListener {
            if (networkModule.isConnected()) {
                onResume()
            }
        }

        binding.fragmentListInclude.recyclerView.layoutManager = llm
        switcher.showProgressView()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarInclude.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
            model.getData(id).observe(this, Observer<Data> { result ->
                try {
                    if (result != null) {
                        processResult(result)
                        switcher.showContentView()
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            })
        }
    }

    abstract fun processResult(result: Data)
}
