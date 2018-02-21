package cz.kutner.comicsdb.newsList

import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.NewsItem
import kotlinx.coroutines.experimental.Deferred

class NewsListViewModel(retrofitModule: RetrofitModule) :
    AbstractAndroidViewModel<NewsItem>(retrofitModule) {
    override fun getJob(): Deferred<List<NewsItem>?> =
        retrofitModule.newsListService.listNews(start * count, count)
}