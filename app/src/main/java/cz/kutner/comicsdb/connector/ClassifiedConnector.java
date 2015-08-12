package cz.kutner.comicsdb.connector;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.model.Classified;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 3.6.2015.
 */
public class ClassifiedConnector {
    private static final String LOG_TAG = ClassifiedConnector.class.getSimpleName();

    public static List<Classified> get(int page) {
        String uri = "http://comicsdb.cz/bazar.php" + "?str=" + page;
        return loadFromUri(uri);
    }

    public static List<Classified> getFiltered(int page, String category, String searchText) {
        int categoryId = 0;
        switch (category) {
            case "Prodám":
                categoryId = 1;
                break;
            case "Koupím":
                categoryId = 2;
                break;
            case "Vyměním":
                categoryId = 3;
                break;
            case "Ostatní":
                categoryId = 10;
                break;
        }
        String uri = "http://comicsdb.cz/bazar.php" + "?str=" + page + "&id=" + categoryId + "&val=" + searchText;
        return loadFromUri(uri);
    }

    private static List<Classified> loadFromUri(String uri) {
        List<Classified> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(uri).get();
            for (Element entry : doc.select("div#prispevek")) {
                String nick = entry.select("span.prispevek-nick").get(0).text();
                String category = entry.select("span.prispevek-nick").get(1).text();
                String time = entry.select("span.prispevek-cas").get(0).text();
                String iconUrl = entry.select("div#prispevek-icon").select("img").first().attr("src");
                for (Element remove : entry.select("span,img")) {
                    remove.remove();
                }
                String text = entry.select("div#prispevek-text").html().replace("| ", "").replace("<br></br>", "");
                Classified classified = new Classified(nick, time, category, text);
                if (!iconUrl.isEmpty()) {
                    classified.setIconUrl(Utils.fixUrl(iconUrl));
                }
                result.add(classified);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }
}

