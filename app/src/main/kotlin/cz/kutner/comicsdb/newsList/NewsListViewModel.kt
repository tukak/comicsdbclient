package cz.kutner.comicsdb.newsList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.NewsItem
import kotlinx.coroutines.experimental.Deferred

class NewsListViewModel(application: Application) :
    AbstractAndroidViewModel<NewsItem>(application) {
    override fun getJob(): Deferred<List<NewsItem>?> =
        retrofitModule.newsListService.listNews(start * count, count)
}