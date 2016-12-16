package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.ComicsParser
import cz.kutner.comicsdb.model.Comics
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ComicsListConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return ComicsListResponseBodyConverter()
    }

    companion object {
        fun create(): ComicsListConverterFactory {
            return ComicsListConverterFactory()
        }
    }
}

class ComicsListResponseBodyConverter : Converter<ResponseBody, List<Comics>> {
    override fun convert(value: ResponseBody): List<Comics>? {
        return ComicsParser().parseComicsList(value.byteStream())
    }
}
