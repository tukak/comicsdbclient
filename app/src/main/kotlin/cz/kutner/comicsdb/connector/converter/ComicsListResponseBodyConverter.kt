package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.ComicsParser
import cz.kutner.comicsdb.model.Comics
import okhttp3.ResponseBody
import retrofit2.Converter

class ComicsListResponseBodyConverter : Converter<ResponseBody, List<Comics>> {
    override fun convert(value: ResponseBody): List<Comics>? {
       return ComicsParser().parseComicsList(value.byteStream())
    }
}
