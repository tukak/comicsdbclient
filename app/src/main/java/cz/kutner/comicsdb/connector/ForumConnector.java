package cz.kutner.comicsdb.connector;

import android.util.Log;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.ComicsDBApplication;
import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.model.ForumEntry;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 3.6.2015.
 */
public class ForumConnector {
    private static final String LOG_TAG = ForumConnector.class.getSimpleName();

    public static List<ForumEntry> getFiltered(int page, String forum, String searchText) {
        int forumId = 0;
        switch (forum) {
            case "* Připomínky a návrhy":
                forumId = 1;
                break;
            case "Fabula Rasa":
                forumId = 10;
                break;
            case "Filmový klub":
                forumId = 5;
                break;
            case "Pindárna":
                forumId = 3;
                break;
            case "Povinná četba":
                forumId = 4;
                break;
            case "Poznej comics nebo postavu":
                forumId = 9;
                break;
            case "Sběratelský klub":
                forumId = 6;
                break;
            case "Slevy, výprodeje, bazary":
                forumId = 11;
                break;
            case "Srazy, cony, festivaly":
                forumId = 8;
                break;
            case "Stripy, jouky, fejky :)":
                forumId = 8;
                break;
        }

        String uri = "http://comicsdb.cz/forum.php" + "?str=" + page + "&id=" + forumId + "&val=" + searchText;
        return loadFromUri(uri);
    }


    public static List<ForumEntry> get(int page) {
        String uri = "http://comicsdb.cz/forum.php" + "?str=" + page;
        return loadFromUri(uri);
    }

    private static List<ForumEntry> loadFromUri(String uri) {
        List<ForumEntry> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(uri).get();
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
                    forumEntry.setIconUrl(Utils.fixUrl(iconUrl));
                }
                result.add(forumEntry);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }

}
