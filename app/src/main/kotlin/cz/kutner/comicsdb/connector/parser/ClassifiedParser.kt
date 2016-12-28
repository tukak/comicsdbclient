package cz.kutner.comicsdb.connector.parser

import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.utils.Utils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.InputStream
import java.util.*

class ClassifiedParser {
    fun parseClassified(html: InputStream, encoding: String = "windows-1250"): List<Classified> {
        val result = ArrayList<Classified>()
        val doc: Document = Jsoup.parse(html, encoding, "")
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
        return result
    }
}