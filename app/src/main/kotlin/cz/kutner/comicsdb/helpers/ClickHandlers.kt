package cz.kutner.comicsdb.helpers

import android.content.Intent
import android.view.View
import cz.kutner.comicsdb.authorDetail.AuthorDetailActivity
import cz.kutner.comicsdb.comicsDetail.ComicsDetailActivity
import cz.kutner.comicsdb.image.ImageViewSliderActivity
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.model.Image
import cz.kutner.comicsdb.seriesDetail.SeriesDetailActivity
import java.util.*

class ClickHandlers {
    fun onClickAuthor(view: View, authorId: Int) {
        val intent = Intent(view.context, AuthorDetailActivity::class.java)
        intent.putExtra(Intent.EXTRA_UID, authorId)
        view.context.startActivity(intent)
    }

    fun onClickComics(view: View, comicsId: Int) {
        val intent = Intent(view.context, ComicsDetailActivity::class.java)
        intent.putExtra(Intent.EXTRA_UID, comicsId)
        view.context.startActivity(intent)
    }

    fun onClickSeries(view: View, seriesId: Int) {
        val intent = Intent(view.context, SeriesDetailActivity::class.java)
        intent.putExtra(Intent.EXTRA_UID, seriesId)
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