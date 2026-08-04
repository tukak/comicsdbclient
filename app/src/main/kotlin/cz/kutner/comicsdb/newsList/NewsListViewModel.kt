package cz.kutner.comicsdb.newsList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.NewsItem

class NewsListViewModel(private val service: NewsListService) :
    AbstractPagedViewModel<NewsItem>() {
    override suspend fun getJob(): List<NewsItem> =
        service.listNews(start * count, count)
}
