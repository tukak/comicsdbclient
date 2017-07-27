package cz.kutner.comicsdb.activity

import android.app.inject
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.adapter.ImagePagerAdapter
import cz.kutner.comicsdb.model.Image
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.activity_view_images.*

class ImageViewSliderActivity : AppCompatActivity() {

    val firebase by inject<FirebaseAnalytics>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_images)
        val images = intent.getParcelableArrayListExtra<Image>(IMAGES)
        val position = intent.getIntExtra(POSTITION, 0)
        val fragmentPagerAdapter: FragmentStatePagerAdapter = ImagePagerAdapter(supportFragmentManager, images)
        image_pager.adapter = fragmentPagerAdapter
        image_tabs.setupWithViewPager(image_pager)
        image_pager.currentItem = position
        firebase.logVisit(contentName = "Prohlížení obrázků", contentType = "Image")
    }

    companion object {
        val IMAGES: String = "cz.kutner.comicsdbclient.comicsdbclient.images"
        val POSTITION: String = "cz.kutner.comicsdbclient.comicsdbclient.image_position"
    }
}
