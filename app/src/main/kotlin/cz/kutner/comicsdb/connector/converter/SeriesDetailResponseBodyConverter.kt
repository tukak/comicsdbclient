package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.connector.parser.SeriesParser
import cz.kutner.comicsdb.model.Series
import okhttp3.ResponseBody
import retrofit2.Converter

class SeriesDetailResponseBodyConverter : Converter<ResponseBody, Series> {
    override fun convert(value: ResponseBody): Series? {
        return SeriesParser().parseSeriesDetail(value.byteStream())
    }
}
