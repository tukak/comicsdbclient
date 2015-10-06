package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.model.Comment
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Node
import org.jsoup.parser.Parser
import retrofit.converter.ConversionException
import retrofit.converter.Converter
import retrofit.mime.TypedInput
import retrofit.mime.TypedOutput
import java.lang.reflect.Type

public class ComicsConverter : Converter {

    @Throws(ConversionException::class)
    override fun fromBody(body: TypedInput, type: Type): Any {
        val comics = Comics()
        val doc: Document
        var sibling: Node?
        try {
            doc = Jsoup.parse(body.`in`(), "windows-1250", "")
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
            val coverElements = doc.select("img[title=Obálka]")
            if (coverElements.size() > 0) {
                val coverURI = coverElements.first().attr("src")
                if (!coverURI.isEmpty()) {
                    comics.coverUrl = Utils.fixUrl(coverURI)
                }
            }

            for (titulek in doc.select(".titulek")) {
                val title_name = titulek.text().substring(0, titulek.text().length() - 1)
                val title_value = titulek.nextSibling()
                when (title_name) {
                    "Žánr" -> {
                        val genre = title_value.toString().replace("&nbsp;".toRegex(), " ")
                        comics.genre = Parser.unescapeEntities(genre.substring(1, genre.length() - 1), false)
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
                        var authors = ""
                        sibling = title_value.nextSibling()
                        authors += Jsoup.parse(sibling.outerHtml()).text()
                        authors += " "
                        sibling = sibling.nextSibling()
                        authors += Jsoup.parse(sibling.outerHtml()).text()
                        authors += "\n"
                        sibling = sibling.nextSibling()
                        while (true) {
                            if (!sibling.toString().startsWith("<br")) {
                                if (sibling.toString().startsWith("[")) {
                                    authors += Jsoup.parse(sibling?.outerHtml()).text()
                                    authors += " "
                                    sibling = sibling?.nextSibling()
                                    authors += Jsoup.parse(sibling?.outerHtml()).text()
                                }
                                authors += "\n"
                            }
                            sibling = sibling?.nextSibling()
                            if (sibling == null) {
                                break
                            }
                            if (sibling.toString().startsWith("<span")) {
                                break
                            }
                        }
                        comics.authors = Parser.unescapeEntities(authors, false)
                    }
                    "Série" -> comics.series = Parser.unescapeEntities(Jsoup.parse(title_value.outerHtml()).text(), false)
                }
            }
            for (comment in doc.select("div#prispevek")) {
                val nick = comment.select("span.prispevek-nick").first().text()
                val time = comment.select("span.prispevek-cas").first().text()
                val stars = comment.select("img.star").size()
                val iconUrl = comment.select("div#prispevek-icon").select("img").first().attr("src")
                for (remove in comment.select("span,img")) {
                    remove.remove()
                }
                val commentText = comment.text().replace("| ", "")
                val commentObject = Comment(nick, stars, commentText, time)
                if (!iconUrl.isEmpty()) {
                    commentObject.iconUrl = Utils.fixUrl(iconUrl)
                }
                comics.addComment(commentObject)
            }
        } catch (e: Exception) {
        }

        return comics
    }

    override fun toBody(`object`: Any): TypedOutput? {
        return null
    }
}
