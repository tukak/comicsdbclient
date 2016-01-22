package cz.kutner.comicsdb.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.SeriesDetailFragment
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

public class SeriesDetailActivity : AppCompatActivity() {

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            searchView.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupToolbar()
        val intent = intent
        var id: Int? = null
        if (Intent.ACTION_VIEW == intent.action) {
            //volá nás někdo přes URL
            try {
                id = Integer.parseInt(intent.dataString.removePrefix(getString(R.string.url_series_detail)))
            } catch (e: Exception) {
            }

        } else {
            id = intent.getIntExtra(MainActivity.SERIES_ID, 0)
        }
        val fragment = SeriesDetailFragment.newInstance()
        val args = Bundle()
        args.putInt("id", id!!)
        fragment.arguments = args
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            startActivity(intentFor<MainActivity>().singleTop().clearTop())
        }
        return super.onOptionsItemSelected(item)
    }
}