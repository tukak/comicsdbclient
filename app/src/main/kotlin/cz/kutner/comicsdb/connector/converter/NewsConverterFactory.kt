package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.NewsParser
import cz.kutner.comicsdb.model.NewsItem
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NewsConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return NewsResponseBodyConverter()
    }

    companion object {
        fun create(): NewsConverterFactory {
            return NewsConverterFactory()
        }
    }
}

class NewsResponseBodyConverter : Converter<ResponseBody, List<NewsItem>> {

    override fun convert(value: ResponseBody): List<NewsItem>? {
        return NewsParser().parseNews(value.byteStream())
    }
}
