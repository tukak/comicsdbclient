package cz.kutner.comicsdb.connector;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.model.ForumEntry;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 3.6.2015.
 */
public class ForumConnector {
    private static final String LOG_TAG = ForumConnector.class.getSimpleName();

    public static List<ForumEntry> get(int page) {
        List<ForumEntry> result = new ArrayList<>();
        Document doc;
        try {
            String url = "http://comicsdb.cz/forum.php" + "?str=" + page;
            doc = Jsoup.connect(url).get();
            for (Element entry : doc.select("div#prispevek")) {
                String nick = entry.select("span.prispevek-nick").get(0).text();
                String forum = entry.select("span.prispevek-nick").get(1).text();
                String time = entry.select("span.prispevek-cas").get(0).text();
                String iconUrl = entry.select("div#prispevek-icon").select("img").first().attr("src");
                for (Element remove : entry.select("span,img")) {
                    remove.remove();
                }
                String text = entry.select("div#prispevek-text").html().replace("| ", "").replace("<br></br>", "");
                ForumEntry forumEntry = new ForumEntry(nick, time, forum, text);
                if (!iconUrl.isEmpty()) {
                    forumEntry.setIcon(Utils.getFromCacheOrDownload(iconUrl));
                }
                result.add(forumEntry);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }

}
