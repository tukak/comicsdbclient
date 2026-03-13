package cz.kutner.comicsdb.image

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.core.content.IntentCompat
import cz.kutner.comicsdb.model.Image
import cz.kutner.comicsdb.ui.theme.ComicsDBTheme

class ImageViewSliderActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val images = IntentCompat.getParcelableArrayListExtra(intent, IMAGES, Image::class.java) ?: arrayListOf()
        val position = intent.getIntExtra(POSTITION, 0)
        setContent {
            ComicsDBTheme {
                ImageViewerScreen(images = images, initialPosition = position)
            }
        }
    }

    companion object {
        const val IMAGES: String = "cz.kutner.comicsdbclient.comicsdbclient.images"
        const val POSTITION: String = "cz.kutner.comicsdbclient.comicsdbclient.image_position"
    }
}
