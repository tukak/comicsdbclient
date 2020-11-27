package cz.kutner.comicsdb.newsList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.NewsItem
import cz.kutner.comicsdb.network.RetrofitModule

class NewsListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<NewsItem>(retrofitModule) {
    override suspend fun getJob(): List<NewsItem> =
        retrofitModule.newsListService.listNews(start * count, count)
}