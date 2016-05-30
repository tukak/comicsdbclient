package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.ComicsParser
import cz.kutner.comicsdb.model.Comics
import okhttp3.ResponseBody
import retrofit2.Converter

class ComicsDetailResponseBodyConverter : Converter<ResponseBody, Comics> {

    override fun convert(value: ResponseBody): Comics? {
        return ComicsParser().parseComicsDetail(value.byteStream())
    }

}
