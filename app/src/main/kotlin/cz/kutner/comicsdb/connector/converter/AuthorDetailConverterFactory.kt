package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.AuthorParser
import cz.kutner.comicsdb.model.Author
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

class AuthorDetailResponseBodyConverter : Converter<ResponseBody, Author> {

    override fun convert(value: ResponseBody): Author? {
        return AuthorParser().parseAuthorDetail(value.byteStream())
    }
}
