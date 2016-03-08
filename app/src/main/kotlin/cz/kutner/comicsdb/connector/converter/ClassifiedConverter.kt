package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.utils.Utils
import cz.kutner.comicsdb.model.Classified
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit.converter.ConversionException
import retrofit.converter.Converter
import retrofit.mime.TypedInput
import retrofit.mime.TypedOutput
import java.lang.reflect.Type
import java.util.*

class ClassifiedConverter : Converter {
    @Throws(ConversionException::class)
    override fun fromBody(body: TypedInput, type: Type): Any {
        val result = ArrayList<Classified>()
        val doc: Document
        try {
            doc = Jsoup.parse(body.`in`(), "windows-1250", "")
            for (entry in doc.select("div#prispevek")) {
                val nick = entry.select("span.prispevek-nick")[0].text()
                val category = entry.select("span.prispevek-nick")[1].text()
                val time = entry.select("span.prispevek-cas")[0].text()
                val iconUrl = entry.select("div#prispevek-icon").select("img").first().attr("src")
                for (remove in entry.select("span,img")) {
                    remove.remove()
                }
                val text = entry.select("div#prispevek-text").html().replace("| ", "").replace("<br></br>", "")
                val classified = Classified(nick, time, category, text)
                if (!iconUrl.isEmpty()) {
                    classified.iconUrl = Utils.fixUrl(iconUrl)
                }
                result.add(classified)
            }
        } catch (e: Exception) {
        }

        return result
    }

    override fun toBody(`object`: Any): TypedOutput? {
        return null
    }
}
