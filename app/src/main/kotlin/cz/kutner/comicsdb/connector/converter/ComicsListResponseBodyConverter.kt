package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.model.Comics
import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import retrofit2.Converter
import java.util.*

class ComicsListResponseBodyConverter : Converter<ResponseBody, List<Comics>>, AnkoLogger {
    override fun convert(value: ResponseBody): List<Comics>? {
        info("Začínáme konvertovat")
        val result = ArrayList<Comics>()
        val doc: Document
        val table: Element
        try {
            doc = Jsoup.parse(value.byteStream(), "windows-1250", "")
            if (doc.select("title").text().contentEquals("ComicsDB | vyhledávání")) {
                table = doc.select("div.search-title:contains(COMICSY) + table[summary=Přehled comicsů]").first()
            } else {
                table = doc.select("table[summary=Přehled comicsů]").first()
            }
            for (row in table.select("tbody tr")) {
                val columns = row.select("td")
                val title = columns[0].select("a").first().text()
                //val id = Integer.parseInt(columns.get(0).select("a").first().attr("href").replaceFirst("^.*\\D", "")) //gets the id in the end of the url
                val id = Integer.parseInt(columns[0].select("a").first().attr("href").removePrefix("comics.php?id="))
                val year = columns[1].text()
                var rating = columns[3].text()
                if (rating.isEmpty()) {
                    rating = "0"
                }
                val comics = Comics(title, id)
                comics.published = year
                comics.rating = Integer.valueOf(rating)
                result.add(comics)
            }
            info("Konverze hotova")
        } catch (e: Exception) {
            error(e.message)
        }
        return result
    }
}
