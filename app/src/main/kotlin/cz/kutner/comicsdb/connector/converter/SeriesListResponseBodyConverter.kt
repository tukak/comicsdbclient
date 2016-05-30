package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.SeriesParser
import cz.kutner.comicsdb.model.Series
import okhttp3.ResponseBody
import retrofit2.Converter

class SeriesListResponseBodyConverter : Converter<ResponseBody, List<Series>> {

    override fun convert(value: ResponseBody): List<Series>? {
        return SeriesParser().parseSeriesList(value.byteStream())
    }
}
