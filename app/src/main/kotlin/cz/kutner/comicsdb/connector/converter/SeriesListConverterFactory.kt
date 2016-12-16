package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.SeriesParser
import cz.kutner.comicsdb.model.Series
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SeriesListConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return SeriesListResponseBodyConverter()
    }

    companion object {
        fun create(): SeriesListConverterFactory {
            return SeriesListConverterFactory()
        }
    }
}

class SeriesListResponseBodyConverter : Converter<ResponseBody, List<Series>> {

    override fun convert(value: ResponseBody): List<Series>? {
        return SeriesParser().parseSeriesList(value.byteStream())
    }
}
