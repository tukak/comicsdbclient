package cz.kutner.comicsdb.connector.parser

import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.utils.fixUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import java.io.InputStream
import java.util.*

class AuthorParser {
    fun parseAuthorDetail(html: InputStream, encoding: String = "windows-1250"): Author {
        val doc: Document = Jsoup.parse(html, encoding, "")
        val name = doc.select("div#wrapper div#leftcolumn h5").text()
        val id = doc.select("#filtrform > input:nth-child(2)").attr("value").toInt()
        val country = doc.select("div#wrapper div#leftcolumn").html().substringBefore("<br").substringAfter("</h5>").trim()
        val result = Author(name, country, id)
        val photoElements = doc.select("div#comicspic img")
        if (photoElements.size > 0) {
            val photoURI = photoElements.first().attr("src")
            if (!photoURI.isEmpty()) {
                result.photoUrl= photoURI.fixUrl()
            }
        }
        for (titulek in doc.select(".titulek")) {
            val title_name = titulek.text().substring(0, titulek.text().length - 1)
            val title_value = titulek.nextSibling()
            when (title_name) {
                "Biografie" -> {
                    var description = ""
                    var sibling = title_value.nextSibling()
                    while (true) {
                        description += sibling.toString()
                        sibling = sibling?.nextSibling()
                        if (sibling.toString().startsWith("<span")) {
                            break
                        }
                    }
                    result.bio = Parser.unescapeEntities(description, false)
                }
                "Poznámky" -> {
                    var notes = ""
                    var sibling = title_value.nextSibling()
                    while (true) {
                        notes += sibling.toString()
                        sibling = sibling.nextSibling()
                        if (sibling == null || sibling.toString().startsWith("<span")) {
                            break
                        }
                    }
                    result.notes = Parser.unescapeEntities(notes, false)
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

    fun parseAuthorList(html: InputStream, encoding: String = "windows-1250"): ArrayList<Author> {
        val result = ArrayList<Author>()
        val doc: Document = Jsoup.parse(html, encoding, "")
        val table: Element?
        if (doc.select("title").text().contentEquals("ComicsDB | vyhledávání")) {
            if (doc.select("div.search-title:contains(AUTOŘI) + table[summary=Přehled comicsů]").size > 0) {
                table = doc.select("div.search-title:contains(AUTOŘI) + table[summary=Přehled comicsů]").first()
            } else {
                return result
            }
        } else {
            table = doc.select("table[summary=Přehled comicsů]").first()
        }
        if (table != null) {
            for (row in table.select("tbody tr")) {
                val columns = row.select("td")
                val name = columns[0].select("a").first().text()
                val id = Integer.parseInt(columns[0].select("a").first().attr("href").removePrefix("autor.php?id="))
                val country = columns[1].text()
                val author = Author(name, country, id)
                result.add(author)
            }
        }
        return result
    }
}