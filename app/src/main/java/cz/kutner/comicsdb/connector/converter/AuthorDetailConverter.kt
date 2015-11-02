package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.AuthorParser
import retrofit.converter.ConversionException
import retrofit.converter.Converter
import retrofit.mime.TypedInput
import retrofit.mime.TypedOutput
import java.lang.reflect.Type

public class AuthorDetailConverter : Converter {

    @Throws(ConversionException::class)
    override fun fromBody(body: TypedInput, type: Type): Any {
        return AuthorParser().parseAuthorDetail(body.`in`())
    }

    override fun toBody(`object`: Any): TypedOutput? {
        return null
    }
}
