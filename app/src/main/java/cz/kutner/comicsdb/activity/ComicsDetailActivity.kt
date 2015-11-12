package cz.kutner.comicsdb.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.fragment.ComicsDetailFragment
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

public class ComicsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val actionBar = supportActionBar
        actionBar.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        var id: Int? = null
        if (Intent.ACTION_VIEW == intent.action) {
            //volá nás někdo přes URL
            try {
                id = Integer.parseInt(intent.dataString.removePrefix("http://comicsdb.cz/comics.php?id="))
            } catch (e: Exception) {
            }

        } else {
            id = intent.getIntExtra(MainActivity.COMICS_ID, 0)
        }
        val fragment = ComicsDetailFragment.newInstance()
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
