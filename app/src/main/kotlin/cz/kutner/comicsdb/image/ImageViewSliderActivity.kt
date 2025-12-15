package cz.kutner.comicsdb.image

import android.os.Bundle
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.appcompat.app.AppCompatActivity
import cz.kutner.comicsdb.databinding.ActivityViewImagesBinding
import cz.kutner.comicsdb.model.Image

class ImageViewSliderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val images = intent.getParcelableArrayListExtra<Image>(IMAGES)
        val position = intent.getIntExtra(POSTITION, 0)
        val fragmentPagerAdapter: FragmentStatePagerAdapter =
            ImagePagerAdapter(supportFragmentManager, images)
        binding.imagePager.adapter = fragmentPagerAdapter
        binding.imageTabs.setupWithViewPager(binding.imagePager)
        binding.imagePager.currentItem = position
    }

    companion object {
        const val IMAGES: String = "cz.kutner.comicsdbclient.comicsdbclient.images"
        const val POSTITION: String = "cz.kutner.comicsdbclient.comicsdbclient.image_position"
    }
}
