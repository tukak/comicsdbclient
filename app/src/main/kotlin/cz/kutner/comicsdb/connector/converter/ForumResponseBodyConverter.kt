package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.ForumParser
import cz.kutner.comicsdb.model.ForumEntry
import okhttp3.ResponseBody
import retrofit2.Converter

class ForumResponseBodyConverter : Converter<ResponseBody, List<ForumEntry>> {

    override fun convert(value: ResponseBody): List<ForumEntry>? {
        return ForumParser().parseForum(value.byteStream())
    }
}
