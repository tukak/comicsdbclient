package cz.kutner.comicsdb.connector.converter

import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ClassifiedConverterFactory : Converter.Factory(), AnkoLogger {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return ClassifiedResponseBodyConverter()
    }

    companion object {
        fun create(): ClassifiedConverterFactory {
            return ClassifiedConverterFactory();
        }
    }
}

