package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.SeriesParser
import cz.kutner.comicsdb.model.Series
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SeriesDetailConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return SeriesDetailResponseBodyConverter()
    }

    companion object {
        fun create(): SeriesDetailConverterFactory {
            return SeriesDetailConverterFactory()
        }
    }
}


class SeriesDetailResponseBodyConverter : Converter<ResponseBody, Series> {
    override fun convert(value: ResponseBody): Series? {
        return SeriesParser().parseSeriesDetail(value.byteStream())
    }
}
