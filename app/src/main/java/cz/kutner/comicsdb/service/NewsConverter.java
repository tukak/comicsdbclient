package cz.kutner.comicsdb.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.NewsItem;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import timber.log.Timber;

public class NewsConverter implements Converter {

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        List<NewsItem> result = new ArrayList<>();
        try {
            Document  doc = Jsoup.parse(body.in(), "windows-1250", "");
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
            Timber.e(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
