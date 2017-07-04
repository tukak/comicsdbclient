package cz.kutner.comicsdb.connector.parser

import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.utils.fixUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.InputStream
import java.util.*

class ForumParser {
    fun parseForum(html: InputStream, encoding: String = "windows-1250"): List<ForumEntry> {
        val result = ArrayList<ForumEntry>()
        val doc: Document = Jsoup.parse(html, encoding, "")
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
                    forumEntry.iconUrl = iconUrl.fixUrl()
                }
                result.add(forumEntry)
            }
        return result
    }
}