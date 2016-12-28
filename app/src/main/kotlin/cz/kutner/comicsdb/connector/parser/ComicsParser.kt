package cz.kutner.comicsdb.connector.parser

import cz.kutner.comicsdb.model.*
import cz.kutner.comicsdb.utils.Utils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.parser.Parser
import java.io.InputStream
import java.util.*

class ComicsParser {
    fun parseComicsDetail(html: InputStream, encoding: String = "windows-1250"): Comics {
        val comics = Comics("", 0)
        val doc: Document = Jsoup.parse(html, encoding, "")
        var sibling: Node?
        comics.id = Integer.parseInt(doc.select("input[name=id]").first().attr("value"))
        val name = doc.select("H5").text()
        comics.name = Parser.unescapeEntities(name, false)
        // rating - #rating_block - vrací Celkové hodnocení 67% (26) 1 2 3 4 5
        val rating_and_count = doc.select("#rating_block h2").first().nextSibling().toString()
        if (rating_and_count.contains("%")) {
            val rating = rating_and_count.substring(1, rating_and_count.indexOf('%'))
            comics.rating = Integer.valueOf(rating)
            val votes = rating_and_count.substring(rating_and_count.indexOf('(') + 1, rating_and_count.indexOf(')'))
            comics.voteCount = Integer.valueOf(votes)
        } else {
            comics.rating = 0
            comics.voteCount = 0
        }

        val cover = doc.select("a[data-title=Obálka]")
        if (cover.size > 0) {
            val fullCoverURI = cover.first().attr("href")
            val preview = cover.select("img")
            val previewURI = preview.first().attr("src")

            val previewUrl = Utils.fixUrl(previewURI)
            val fullUrl = Utils.fixUrl(fullCoverURI)
            val caption = preview.attr("title")

            comics.cover = Image(previewUrl, fullUrl, caption)
        }

        val samples = doc.select("a[data-title*=Ukázka]")
        for (sample in samples) {
            val fullImageURI = sample.attr("href")
            val preview = sample.select("img")
            val previewURI = preview.first().attr("src")

            val previewUrl = Utils.fixUrl(previewURI)
            val fullUrl = Utils.fixUrl(fullImageURI)
            val caption = preview.attr("title")
            comics.samples.add(Image(previewUrl, fullUrl, caption))
        }

        for (titulek in doc.select(".titulek")) {
            val title_name = titulek.text().substring(0, titulek.text().length - 1)
            val title_value = titulek.nextSibling()
            when (title_name) {
                "Žánr" -> {
                    val genre = title_value.toString().replace("&nbsp;".toRegex(), " ")
                    if (genre.length > 1) {
                        comics.genre = Parser.unescapeEntities(genre.substring(1, genre.length - 1), false)
                    }
                }
                "Vydal" -> comics.publisher = Parser.unescapeEntities(Jsoup.parse(title_value.nextSibling().outerHtml()).text(), false)
                "Rok a měsíc vydání" -> comics.published = Parser.unescapeEntities(title_value.toString(), false)
                "ISSN" -> comics.issn = Parser.unescapeEntities(title_value.toString(), false)
                "Vydání" -> comics.issueNumber = Parser.unescapeEntities(title_value.toString(), false)
                "Vazba" -> comics.binding = Parser.unescapeEntities(title_value.toString(), false)
                "Format" -> comics.format = Parser.unescapeEntities(title_value.toString(), false)
                "Počet stran" -> comics.pagesCount = Parser.unescapeEntities(title_value.toString(), false)
                "Tisk" -> comics.print = Parser.unescapeEntities(title_value.toString(), false)
                "Název originálu" -> comics.originalName = Parser.unescapeEntities(title_value.toString(), false)
                "Vydavatel originálu" -> comics.originalPublisher = Parser.unescapeEntities(title_value.toString(), false)
                "Cena v době vydání" -> comics.price = Parser.unescapeEntities(title_value.toString(), false)
                "Popis" -> {
                    var description = ""
                    sibling = title_value.nextSibling()
                    while (true) {
                        description += sibling.toString()
                        sibling = sibling?.nextSibling()
                        if (sibling.toString().startsWith("<span")) {
                            break
                        }
                    }
                    comics.description = Parser.unescapeEntities(description, false)
                }
                "Poznámky" -> {
                    var notes = ""
                    sibling = title_value.nextSibling()
                    while (true) {

                        notes += sibling.toString()
                        sibling = sibling?.nextSibling()
                        if (sibling.toString().startsWith("<span")) {
                            break
                        }
                    }
                    comics.notes = Parser.unescapeEntities(notes, false)
                }
                "Autoři" -> {
                    sibling = title_value.nextSibling()
                    while (true) {
                        if (!sibling.toString().startsWith("<br")) {
                            if (sibling.toString().trim().startsWith("[")) {
                                val authorRole = Jsoup.parse(sibling!!.outerHtml()).text().trim()
                                sibling = sibling.nextSibling()
                                val authorName = Jsoup.parse(sibling.outerHtml()).text()
                                val authorId = Integer.parseInt(sibling.attr("href").removePrefix("autor.php?id="))
                                val author = Author(authorName, null, authorId)
                                author.role = authorRole
                                comics.authors.add(author)
                            }
                        }
                        sibling = sibling?.nextSibling()
                        if (sibling == null) {
                            break
                        }
                        if (sibling.toString().startsWith("<span")) {
                            break
                        }
                    }
                }
                "Série" -> comics.series = Series(name = Parser.unescapeEntities(Jsoup.parse(title_value.outerHtml()).text(), false), id = Integer.parseInt(title_value.attr("href").removePrefix("serie.php?id=")), numberOfComicses = 0)
            }
        }
        for (comment in doc.select("div#prispevek")) {
            val nick = comment.select("span.prispevek-nick").first().text()
            val time = comment.select("span.prispevek-cas").first().text()
            val stars = comment.select("img.star").size
            val iconUrl = comment.select("div#prispevek-icon").select("img").first().attr("src")
            for (remove in comment.select("span,img")) {
                remove.remove()
            }
            val commentText = comment.text().replace("| ", "")
            val commentObject = Comment(nick, stars, commentText, time)
            if (!iconUrl.isEmpty()) {
                commentObject.iconUrl = Utils.fixUrl(iconUrl)
            }
            comics.comments.add(commentObject)
        }
        return comics
    }

    fun parseComicsList(html: InputStream, encoding: String = "windows-1250"): List<Comics> {
        val result = ArrayList<Comics>()
        val doc: Document = Jsoup.parse(html, encoding, "")
        val table: Element
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
        return result
    }
}