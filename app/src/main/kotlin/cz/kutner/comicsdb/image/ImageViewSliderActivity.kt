package cz.kutner.comicsdb.image

import android.os.Bundle
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.model.Image
import cz.kutner.comicsdb.utils.logVisit
import kotlinx.android.synthetic.main.activity_view_images.*
import org.koin.android.ext.android.inject

class ImageViewSliderActivity : AppCompatActivity() {

    val firebase by inject<FirebaseAnalytics>()

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
        firebase.logVisit(contentName = "Prohlížení obrázků", contentType = "Image")
    }

    companion object {
        const val IMAGES: String = "cz.kutner.comicsdbclient.comicsdbclient.images"
        const val POSTITION: String = "cz.kutner.comicsdbclient.comicsdbclient.image_position"
    }
}
