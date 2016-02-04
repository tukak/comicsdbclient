package cz.kutner.comicsdb.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import cz.kutner.comicsdb.R
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

abstract class AbstractDetailActivity : AppCompatActivity() {

    abstract val prefix: String
    abstract val extraName: String
    abstract val fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupToolbar()
        val intent = intent
        var id: Int? = null
        if (Intent.ACTION_VIEW == intent.action) {
            //volá nás někdo přes URL
            try {
                id = Integer.parseInt(intent.dataString.removePrefix(prefix))
            } catch (e: Exception) {
            }

        } else {
            id = intent.getIntExtra(extraName, 0)
        }

        val args = Bundle()
        args.putInt("id", id!!)
        fragment.arguments = args
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
        }

    }

    fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            searchView.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            startActivity(intentFor<MainActivity>().singleTop().clearTop())
        }
        return super.onOptionsItemSelected(item)
    }
}
