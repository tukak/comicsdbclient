package cz.kutner.comicsdb.connector.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class AuthorDetailConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return AuthorDetailResponseBodyConverter()
    }

    companion object {
        fun create(): AuthorDetailConverterFactory {
            return AuthorDetailConverterFactory()
        }
    }
}

