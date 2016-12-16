package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.ClassifiedParser
import cz.kutner.comicsdb.model.Classified
import okhttp3.ResponseBody
import retrofit2.Converter

class ClassifiedResponseBodyConverter : Converter<ResponseBody, List<Classified>> {

    override fun convert(value: ResponseBody): List<Classified>? {
        return ClassifiedParser().parseClassified(value.byteStream())
    }
}
