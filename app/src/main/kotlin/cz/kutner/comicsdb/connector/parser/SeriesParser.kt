package cz.kutner.comicsdb.connector.parser

import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.model.Series
import org.jetbrains.anko.AnkoLogger
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.InputStream
import java.util.*

class SeriesParser : AnkoLogger {
    fun parseSeriesDetail(html: InputStream, encoding: String = "windows-1250"): Series {
        val doc: Document
        doc = Jsoup.parse(html, encoding, "")
        val name = doc.select("div#wrapper div#leftcolumn h5").text()
        val id = doc.select("#filtrform > input:nth-child(2)").attr("value").toInt()
        val result = Series(name, id, 0)
        for (titulek in doc.select(".titulek")) {
            val title_name = titulek.text().substring(0, titulek.text().length - 1)
            val title_value = titulek.nextSibling()
            when (title_name) {
                "Comicsů v databázi" -> {
                    result.numberOfComicses = title_value.toString().toInt()
                }
            }
        }

        val table = doc.select("table").first()
        for (row in table.select("tbody tr")) {
            val columns = row.select("td")
            val title = columns[0].select("a").first().text()
            val comics_id = Integer.parseInt(columns[0].select("a").first().attr("href").removePrefix("comics.php?id="))
            val year = columns[1].text()
            var rating = columns[3].text()
            if (rating.isEmpty()) {
                rating = "0"
            }
            val comics = Comics(title, comics_id)
            comics.published = year
            comics.rating = Integer.valueOf(rating)
            result.comicses.add(comics)
        }
        return result
    }

    fun parseSeriesList(html: InputStream, encoding: String = "windows-1250"): ArrayList<Series> {
        val result = ArrayList<Series>()
        val doc: Document
        val table: Element
        doc = Jsoup.parse(html, encoding, "")
        if (doc.select("title").text().contentEquals("ComicsDB | vyhledávání")) {
            table = doc.select("div.search-title:contains(SÉRIE) + table[summary=Přehled comicsů]").first()
        } else {
            table = doc.select("table[summary=Přehled comicsů]").first()
        }
        for (row in table.select("tbody tr")) {
            val columns = row.select("td")
            val title = columns[0].select("a").first().text()
            val id = Integer.parseInt(columns[0].select("a").first().attr("href").removePrefix("serie.php?id="))
            val numberOfComicses = Integer.parseInt(columns[1].select("td").text())
            val series = Series(title, id, numberOfComicses)
            result.add(series)
        }
        return result
    }
}