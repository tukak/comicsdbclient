package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.model.Series
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import retrofit.converter.ConversionException
import retrofit.converter.Converter
import retrofit.mime.TypedInput
import retrofit.mime.TypedOutput
import java.lang.reflect.Type
import java.util.*

public class SeriesConverter : Converter {

    @Throws(ConversionException::class)
    override fun fromBody(body: TypedInput, type: Type): Any {
        val result = ArrayList<Series>()
        val doc: Document
        val table: Element
        try {
            doc = Jsoup.parse(body.`in`(), "windows-1250", "")
            if (doc.select("title").text().contentEquals("ComicsDB | vyhledávání")) {
                table = doc.select("div.search-title:contains(SÉRIE) + table[summary=Přehled comicsů]").first()
            } else {
                table = doc.select("table[summary=Přehled comicsů]").first()
            }
            for (row in table.select("tbody tr")) {
                val columns = row.select("td")
                val title = columns.get(0).select("a").first().text()
                //val id = Integer.parseInt(columns.get(0).select("a").first().attr("href").replaceFirst("^.*\\D", "")) //gets the id in the end of the url
                val id = Integer.parseInt(columns.get(0).select("a").first().attr("href").removePrefix("serie.php?id="))
                val numberOfComicses = Integer.parseInt(columns.get(1).select("td").text())
                val series = Series(title, id, numberOfComicses)
                result.add(series)
            }
        } catch (e: Exception) {
        }

        return result
    }

    override fun toBody(`object`: Any): TypedOutput? {
        return null
    }
}
