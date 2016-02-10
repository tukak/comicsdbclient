package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.model.NewsItem
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jsoup.Jsoup
import retrofit.converter.ConversionException
import retrofit.converter.Converter
import retrofit.mime.TypedInput
import retrofit.mime.TypedOutput
import java.lang.reflect.Type
import java.util.*

class NewsConverter : Converter, AnkoLogger {

    @Throws(ConversionException::class)
    override fun fromBody(body: TypedInput, type: Type): Any {
        val result = ArrayList<NewsItem>()
        try {
            val doc = Jsoup.parse(body.`in`(), "windows-1250", "")
            val news = doc.select(".news")
            for (newsRow in news) {
                val title = newsRow.select(".news-tit").first().text()
                val time = newsRow.select(".news-cas").first().text()
                val nick = newsRow.select(".news-nick").first().text()
                newsRow.select(".news-tit, .news-cas, .news-nick").remove()
                var text = newsRow.html()
                text = text.replace("|", "")
                text = text.replaceFirst("<br />", "")
                text = text.replace("href=\"comics.php", "href=\"http://comicsdb.cz/comics.php")
                val newsItem = NewsItem(title, nick, text, time)
                result.add(newsItem)
            }
        } catch (e: Exception) {
            error(e.message)
        }

        return result
    }

    override fun toBody(`object`: Any): TypedOutput? {
        return null
    }
}
