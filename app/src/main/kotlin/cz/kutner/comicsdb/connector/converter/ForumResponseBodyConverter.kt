package cz.kutner.comicsdb.connector.converter

import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.utils.Utils
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.Converter
import java.util.*

class ForumResponseBodyConverter : Converter<ResponseBody, List<ForumEntry>> {

    override fun convert(value: ResponseBody): List<ForumEntry>? {
        val result = ArrayList<ForumEntry>()
        val doc: Document
        try {
            doc = Jsoup.parse(value.byteStream(), "windows-1250", "")
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
            //error(e.message)
        }
        return result
    }
}
