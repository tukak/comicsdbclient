package cz.kutner.comicsdb.connector.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ClassifiedConverterFactory: Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        if (type !is Class<*>) {
            return null;
        }
        return ClassifiedResponseBodyConverter()
    }

    companion object {
        fun create(): ClassifiedConverterFactory {
            return ClassifiedConverterFactory();
        }
    }
}

