package cz.kutner.comicsdb.connector.converter

import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SeriesListConverterFactory : Converter.Factory(),AnkoLogger {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return SeriesListResponseBodyConverter()
    }

    companion object {
        fun create(): SeriesListConverterFactory {
            return SeriesListConverterFactory()
        }
    }
}