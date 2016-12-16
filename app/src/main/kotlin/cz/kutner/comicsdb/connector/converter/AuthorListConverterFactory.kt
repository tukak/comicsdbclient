package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.AuthorParser
import cz.kutner.comicsdb.model.Author
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class AuthorListConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return AuthorListResponseBodyConverter()
    }

    companion object {
        fun create(): AuthorListConverterFactory {
            return AuthorListConverterFactory()
        }
    }
}

class AuthorListResponseBodyConverter : Converter<ResponseBody, List<Author>> {

    override fun convert(value: ResponseBody): List<Author>? {
        return AuthorParser().parseAuthorList(value.byteStream())
    }
}