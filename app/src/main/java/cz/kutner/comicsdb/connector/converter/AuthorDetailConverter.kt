package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.model.Author
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import retrofit.converter.ConversionException
import retrofit.converter.Converter
import retrofit.mime.TypedInput
import retrofit.mime.TypedOutput
import java.lang.reflect.Type

public class AuthorDetailConverter : Converter {

    @Throws(ConversionException::class)
    override fun fromBody(body: TypedInput, type: Type): Any {
        val doc: Document
        doc = Jsoup.parse(body.`in`(), "windows-1250", "")
        val name = doc.select("div#wrapper div#leftcolumn h5").text()
        val id = doc.select("#filtrform > input:nth-child(2)").attr("value").toInt()
        val country = doc.select("div#wrapper div#leftcolumn").html().substringBefore("<br").substringAfter("</h5>").trim()
        var result = Author(name, country, id)
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
                        sibling = sibling?.nextSibling()
                        if (sibling.toString().startsWith("<span")) {
                            break
                        }
                    }
                    result.notes = Parser.unescapeEntities(notes, false)
                }
            }
        }
        return result

    }

    override fun toBody(`object`: Any): TypedOutput? {
        return null
    }
}
