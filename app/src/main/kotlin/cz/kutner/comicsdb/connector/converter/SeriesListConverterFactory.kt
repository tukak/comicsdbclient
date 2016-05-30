package cz.kutner.comicsdb.connector.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SeriesListConverterFactory: Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        if (type !is Class<*>) {
            return null;
        }
        return SeriesListResponseBodyConverter()
    }

    companion object {
        fun create(): SeriesListConverterFactory {
            return SeriesListConverterFactory();
        }
    }
}