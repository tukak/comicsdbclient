package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.ClassifiedParser
import cz.kutner.comicsdb.model.Classified
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ClassifiedConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return ClassifiedResponseBodyConverter()
    }

    companion object {
        fun create(): ClassifiedConverterFactory {
            return ClassifiedConverterFactory()
        }
    }
}

class ClassifiedResponseBodyConverter : Converter<ResponseBody, List<Classified>> {

    override fun convert(value: ResponseBody): List<Classified>? {
        return ClassifiedParser().parseClassified(value.byteStream())
    }
}