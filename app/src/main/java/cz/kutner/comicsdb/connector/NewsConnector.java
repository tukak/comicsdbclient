package cz.kutner.comicsdb.connector;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.NewsItem;
import hugo.weaving.DebugLog;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 3.6.2015.
 */
public class NewsConnector {
    private static final String LOG_TAG = NewsConnector.class.getSimpleName();

    @DebugLog
    public static List<NewsItem> getNews() {
        String uri = "http://comicsdb.cz//index.php";
        return loadFromUri(uri);
    }

    @DebugLog
    private static List<NewsItem> loadFromUri(String uri) {
        List<NewsItem> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(uri).get();
            Elements news = doc.select(".news");
            for (Element newsRow : news) {
                String title = newsRow.select(".news-tit").first().text();
                String time = newsRow.select(".news-cas").first().text();
                String nick = newsRow.select(".news-nick").first().text();
                newsRow.select(".news-tit, .news-cas, .news-nick").remove();
                String text = newsRow.html();
                text = text.replace("|", "");
                text = text.replaceFirst("<br />", "");
                text = text.replace("href=\"comics.php", "href=\"http://comicsdb.cz/comics.php");
                Log.i(LOG_TAG, text);
                NewsItem newsItem = new NewsItem(title, nick, text, time);
                result.add(newsItem);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }
}
