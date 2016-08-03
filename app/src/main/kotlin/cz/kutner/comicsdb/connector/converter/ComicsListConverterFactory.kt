package cz.kutner.comicsdb.connector.converter

import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ComicsListConverterFactory : Converter.Factory(), AnkoLogger {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return ComicsListResponseBodyConverter()
    }

    companion object {
        fun create(): ComicsListConverterFactory {
            return ComicsListConverterFactory()
        }
    }
}
