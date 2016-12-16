package cz.kutner.comicsdb.connector.parser

import cz.kutner.comicsdb.model.NewsItem
import org.jsoup.Jsoup
import java.io.InputStream
import java.util.*

class NewsParser {
    fun parseNews(html: InputStream, encoding: String = "windows-1250"): List<NewsItem> {
        val result = ArrayList<NewsItem>()
        val doc = Jsoup.parse(html, encoding, "")
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
        return result
    }
}