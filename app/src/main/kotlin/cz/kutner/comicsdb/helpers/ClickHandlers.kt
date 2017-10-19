package cz.kutner.comicsdb.helpers

import android.content.Intent
import android.view.View
import cz.kutner.comicsdb.activity.*
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.model.Image
import java.util.*

class ClickHandlers {
    fun onClickAuthor(view: View, authorId: Int) {
        val intent = Intent(view.context, AuthorDetailActivity::class.java)
        intent.putExtra(MainActivity.ITEM_ID, authorId)
        view.context.startActivity(intent)
    }

    fun onClickComics(view: View, comicsId: Int) {
        val intent = Intent(view.context, ComicsDetailActivity::class.java)
        intent.putExtra(MainActivity.ITEM_ID, comicsId)
        view.context.startActivity(intent)
    }

    fun onClickSeries(view: View, seriesId: Int) {
        val intent = Intent(view.context, SeriesDetailActivity::class.java)
        intent.putExtra(MainActivity.ITEM_ID, seriesId)
        view.context.startActivity(intent)
    }

    fun onClickImage(view: View, comics: ComicsDetail, position: Int) {
        val allImages: ArrayList<Image> = ArrayList()
        allImages.add(comics.cover)
        allImages.addAll(comics.samples)
        val intent = Intent(view.context, ImageViewSliderActivity::class.java)
        intent.putParcelableArrayListExtra(ImageViewSliderActivity.IMAGES, allImages)
        intent.putExtra(ImageViewSliderActivity.POSTITION, position)
        view.context.startActivity(intent)
    }
}