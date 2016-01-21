package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.SeriesParser
import retrofit.converter.ConversionException
import retrofit.converter.Converter
import retrofit.mime.TypedInput
import retrofit.mime.TypedOutput
import java.lang.reflect.Type

public class SeriesConverter : Converter {

    @Throws(ConversionException::class)
    override fun fromBody(body: TypedInput, type: Type): Any {
        return SeriesParser().parseSeriesList(body.`in`())
    }

    override fun toBody(`object`: Any): TypedOutput? {
        return null
    }
}
