package cz.kutner.comicsdb.connector.converter

import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ForumConverterFactory : Converter.Factory(), AnkoLogger {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return ForumResponseBodyConverter()
    }

    companion object {
        fun create(): ForumConverterFactory {
            return ForumConverterFactory();
        }
    }
}

