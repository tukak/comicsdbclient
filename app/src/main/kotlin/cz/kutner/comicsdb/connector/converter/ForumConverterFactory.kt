package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.ForumParser
import cz.kutner.comicsdb.model.ForumEntry
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ForumConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return ForumResponseBodyConverter()
    }

    companion object {
        fun create(): ForumConverterFactory {
            return ForumConverterFactory()
        }
    }
}

class ForumResponseBodyConverter : Converter<ResponseBody, List<ForumEntry>> {

    override fun convert(value: ResponseBody): List<ForumEntry>? {
        return ForumParser().parseForum(value.byteStream())
    }
}
