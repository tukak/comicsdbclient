package cz.kutner.comicsdb.activity

import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.ImagePagerAdapter
import cz.kutner.comicsdb.model.Image
import kotlinx.android.synthetic.main.activity_tabbed.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ImageViewSliderActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as ComicsDBApplication).applicationComponent.inject(this)
        setContentView(R.layout.activity_tabbed)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val images = intent.getParcelableArrayListExtra<Image>(IMAGES)
        val position = intent.getIntExtra(POSTITION, 0)
        val fragmentPagerAdapter: FragmentStatePagerAdapter = ImagePagerAdapter(supportFragmentManager, images, position)
        pager.adapter = fragmentPagerAdapter
        sliding_tabs.setupWithViewPager(pager)

    }

    companion object {
        val IMAGES: String = "cz.kutner.comicsdbclient.comicsdbclient.images"
        val POSTITION: String = "cz.kutner.comicsdbclient.comicsdbclient.image_position"

    }
}
