package cz.kutner.comicsdb.newsList

import android.app.Application
import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.model.NewsItem
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

class NewsListViewModel(application: Application) : AbstractAndroidViewModel<NewsItem>(application) {
    override fun loadData(): Deferred<Unit> {
        job = async(CommonPool) {
            retrofitModule.newsListService.listNews(start * count, count).execute().body()
        }
        return super.loadData()
    }
}