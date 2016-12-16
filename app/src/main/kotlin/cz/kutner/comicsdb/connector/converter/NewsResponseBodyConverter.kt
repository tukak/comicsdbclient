package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.NewsParser
import cz.kutner.comicsdb.model.NewsItem
import okhttp3.ResponseBody
import retrofit2.Converter

class NewsResponseBodyConverter : Converter<ResponseBody, List<NewsItem>> {

    override fun convert(value: ResponseBody): List<NewsItem>? {
        return NewsParser().parseNews(value.byteStream())
    }
}
