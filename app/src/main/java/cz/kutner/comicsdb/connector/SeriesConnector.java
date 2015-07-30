package cz.kutner.comicsdb.connector;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.Comics;
import cz.kutner.comicsdb.model.Series;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 3.6.2015.
 */
public class SeriesConnector {
    private static final String LOG_TAG = SeriesConnector.class.getSimpleName();

    @DebugLog
    public static List<Series> get(int page) {
        String uri = "http://comicsdb.cz/serielist.php" + "?str=" + page;
        return loadFromUri(uri);
    }

    @DebugLog
    private static List<Series> loadFromUri(String uri) {
        List<Series> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(uri).get();
            Element table = doc.select("table[summary=Přehled comicsů]").first();
            for (Element row : table.select("tbody tr")) {
                Elements columns = row.select("td");
                String title = columns.get(0).select("a").first().text();
                Integer id = Integer.parseInt(columns.get(0).select("a").first().attr("href").replaceFirst("^.*\\D", "")); //gets the id in the end of the url
                Integer numberOfComicses = Integer.parseInt(columns.get(1).select("td").text().toString());
                Series series = new Series(title, id, numberOfComicses);
                result.add(series);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }
}
