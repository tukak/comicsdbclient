package cz.kutner.comicsdb.connector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.NewsItem;

public class NewsConnector {


    public static List<NewsItem> getNews() {
        String uri = "http://comicsdb.cz//index.php";
        return loadFromUri(uri);
    }


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
                NewsItem newsItem = new NewsItem(title, nick, text, time);
                result.add(newsItem);
            }
        } catch (Exception e) {
        }
        return result;
    }
}
