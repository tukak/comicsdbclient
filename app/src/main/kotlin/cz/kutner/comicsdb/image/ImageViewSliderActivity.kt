package cz.kutner.comicsdb.image

import android.os.Bundle
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.appcompat.app.AppCompatActivity
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Image
import kotlinx.android.synthetic.main.activity_view_images.*

class ImageViewSliderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_images)
        val images = intent.getParcelableArrayListExtra<Image>(IMAGES)
        val position = intent.getIntExtra(POSTITION, 0)
        val fragmentPagerAdapter: FragmentStatePagerAdapter =
            ImagePagerAdapter(supportFragmentManager, images)
        image_pager.adapter = fragmentPagerAdapter
        image_tabs.setupWithViewPager(image_pager)
        image_pager.currentItem = position
    }

    companion object {
        const val IMAGES: String = "cz.kutner.comicsdbclient.comicsdbclient.images"
        const val POSTITION: String = "cz.kutner.comicsdbclient.comicsdbclient.image_position"
    }
}
