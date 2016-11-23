package cz.kutner.comicsdb.activity

import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.ImagePagerAdapter
import cz.kutner.comicsdb.model.Image
import cz.kutner.comicsdb.utils.Utils
import kotlinx.android.synthetic.main.activity_view_images.*

class ImageViewSliderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as ComicsDBApplication).applicationComponent.inject(this)
        setContentView(R.layout.activity_view_images)
        val images = intent.getParcelableArrayListExtra<Image>(IMAGES)
        val position = intent.getIntExtra(POSTITION, 0)
        val fragmentPagerAdapter: FragmentStatePagerAdapter = ImagePagerAdapter(supportFragmentManager, images)
        image_pager.adapter = fragmentPagerAdapter
        image_tabs.setupWithViewPager(image_pager)
        image_pager.currentItem = position
        Utils.logVisit(contentName = "Prohlížení obrázků", contentType = "Image")
    }

    companion object {
        val IMAGES: String = "cz.kutner.comicsdbclient.comicsdbclient.images"
        val POSTITION: String = "cz.kutner.comicsdbclient.comicsdbclient.image_position"
    }
}
