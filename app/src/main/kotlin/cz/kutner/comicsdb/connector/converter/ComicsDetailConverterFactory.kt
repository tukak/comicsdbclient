package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.ComicsParser
import cz.kutner.comicsdb.model.Comics
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ComicsDetailConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return ComicsDetailResponseBodyConverter()
    }

    companion object {
        fun create(): ComicsDetailConverterFactory {
            return ComicsDetailConverterFactory()
        }
    }
}

class ComicsDetailResponseBodyConverter : Converter<ResponseBody, Comics> {

    override fun convert(value: ResponseBody): Comics? {
        return ComicsParser().parseComicsDetail(value.byteStream())
    }

}
