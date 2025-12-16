package cz.kutner.comicsdb.image

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.google.android.material.tabs.TabLayoutMediator
import cz.kutner.comicsdb.databinding.ActivityViewImagesBinding
import cz.kutner.comicsdb.model.Image

class ImageViewSliderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val images = IntentCompat.getParcelableArrayListExtra(intent, IMAGES, Image::class.java)
        val position = intent.getIntExtra(POSTITION, 0)
        val adapter = ImagePagerAdapter(this, images)
        binding.imagePager.adapter = adapter
        TabLayoutMediator(binding.imageTabs, binding.imagePager) { tab, pos ->
            tab.text = adapter.getPageTitle(pos)
        }.attach()
        binding.imagePager.setCurrentItem(position, false)
    }

    companion object {
        const val IMAGES: String = "cz.kutner.comicsdbclient.comicsdbclient.images"
        const val POSTITION: String = "cz.kutner.comicsdbclient.comicsdbclient.image_position"
    }
}
