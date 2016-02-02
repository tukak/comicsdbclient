package cz.kutner.comicsdb.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import cz.kutner.comicsdb.R
import kotlinx.android.synthetic.main.activity_image_view.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.ctx


public class ImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        setupToolbar()
        val url = intent.getStringExtra(ImageViewActivity.IMAGE_URL)
        Picasso.with(ctx).load(url).into(imageView)

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            searchView.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        public val IMAGE_URL: String = "cz.kutner.comicsdbclient.comicsdbclient.image_url"
    }
}