package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.model.ForumEntry
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit.converter.ConversionException
import retrofit.converter.Converter
import retrofit.mime.TypedInput
import retrofit.mime.TypedOutput
import java.lang.reflect.Type
import java.util.*

public class ForumConverter : Converter, AnkoLogger {

    @Throws(ConversionException::class)
    override fun fromBody(body: TypedInput, type: Type): Any {
        val result = ArrayList<ForumEntry>()
        val doc: Document
        try {
            doc = Jsoup.parse(body.`in`(), "windows-1250", "")
            for (entry in doc.select("div#prispevek")) {
                val nick = entry.select("span.prispevek-nick")[0].text()
                val forum = entry.select("span.prispevek-nick")[1].text()
                val time = entry.select("span.prispevek-cas")[0].text()
                val iconUrl = entry.select("div#prispevek-icon").select("img").first().attr("src")
                for (remove in entry.select("span,img")) {
                    remove.remove()
                }
                val text = entry.select("div#prispevek-text").html().replace("| ", "").replace("<br></br>", "")
                val forumEntry = ForumEntry(nick, time, forum, text)
                if (!iconUrl.isEmpty()) {
                    forumEntry.iconUrl = Utils.fixUrl(iconUrl)
                }
                result.add(forumEntry)
            }
        } catch (e: Exception) {
            error(e.message)
        }

        return result
    }

    override fun toBody(`object`: Any): TypedOutput? {
        return null
    }
}
