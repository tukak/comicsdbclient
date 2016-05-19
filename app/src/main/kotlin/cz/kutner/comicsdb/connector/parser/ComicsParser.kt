package cz.kutner.comicsdb.connector.parser

import cz.kutner.comicsdb.model.*
import cz.kutner.comicsdb.utils.Utils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Node
import org.jsoup.parser.Parser
import java.io.InputStream

class ComicsParser {
    fun parseComicsDetail(html: InputStream, encoding: String = "windows-1250"): Comics {
        val comics = Comics("", 0)
        val doc: Document
        var sibling: Node?
        doc = Jsoup.parse(html, encoding, "")
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
        var coverUrl:String = ""
        var fullCoverUrl:String = ""

        val coverElements = doc.select("img[title=Obálka]")
        if (coverElements.size > 0) {
            val coverURI = coverElements.first().attr("src")
            if (!coverURI.isEmpty()) {
                 coverUrl= Utils.fixUrl(coverURI)
            }
        }
        val fullCoverURIElements = doc.select("a[data-title=Obálka]")
        if (fullCoverURIElements.size > 0) {
            val fullCoverURI = fullCoverURIElements.first().attr("href")
            if (!fullCoverURI.isEmpty()) {
                fullCoverUrl = Utils.fixUrl(fullCoverURI)
            }
        }
        comics.cover = Image(coverUrl, fullCoverUrl)
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
                                comics.addAuthor(author)
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
                "Série" -> comics.series = Series(name=Parser.unescapeEntities(Jsoup.parse(title_value.outerHtml()).text(), false), id=Integer.parseInt(title_value.attr("href").removePrefix("serie.php?id=")), numberOfComicses = 0)
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
            comics.addComment(commentObject)
        }
        return comics
    }
}