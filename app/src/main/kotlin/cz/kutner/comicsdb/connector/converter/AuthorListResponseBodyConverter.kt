package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.AuthorParser
import cz.kutner.comicsdb.model.Author
import okhttp3.ResponseBody
import retrofit2.Converter

class AuthorListResponseBodyConverter : Converter<ResponseBody, List<Author>> {

    override fun convert(value: ResponseBody): List<Author>? {
        return AuthorParser().parseAuthorList(value.byteStream())
    }
}
