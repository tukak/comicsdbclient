package cz.kutner.comicsdb.helpers

import android.content.Intent
import android.view.View
import cz.kutner.comicsdb.activity.AuthorDetailActivity
import cz.kutner.comicsdb.activity.ComicsDetailActivity
import cz.kutner.comicsdb.activity.MainActivity
import cz.kutner.comicsdb.activity.SeriesDetailActivity

class ClickHandlers {
    fun onClickAuthor(view: View, authorId: Int) {
        val intent = Intent(view.context, AuthorDetailActivity::class.java)
        intent.putExtra(MainActivity.AUTHOR_ID, authorId)
        view.context.startActivity(intent)
    }

    fun onClickComics(view: View, comicsId: Int) {
        val intent = Intent(view.context, ComicsDetailActivity::class.java)
        intent.putExtra(MainActivity.COMICS_ID, comicsId)
        view.context.startActivity(intent)
    }

    fun onClickSeries(view: View, seriesId: Int) {
        val intent = Intent(view.context, SeriesDetailActivity::class.java)
        intent.putExtra(MainActivity.SERIES_ID, seriesId)
        view.context.startActivity(intent)
    }
}