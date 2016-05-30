package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.AuthorParser
import cz.kutner.comicsdb.model.Author
import okhttp3.ResponseBody
import retrofit2.Converter

class AuthorDetailResponseBodyConverter : Converter<ResponseBody, Author> {

    override fun convert(value: ResponseBody): Author? {
        return AuthorParser().parseAuthorDetail(value.byteStream())
    }
}
