package cz.kutner.comicsdb.connector.converter

import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class AuthorListConverterFactory : Converter.Factory(), AnkoLogger {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return AuthorListResponseBodyConverter()
    }

    companion object {
        fun create(): AuthorListConverterFactory {
            return AuthorListConverterFactory();
        }
    }
}