package cz.kutner.comicsdb.connector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.Series;

public class SeriesConnector {


    public static List<Series> get(int page) {
        String uri = "http://comicsdb.cz/serielist.php" + "?str=" + page;
        return loadFromUri(uri);
    }

    public static List<Series> search(String searchText) {
        String uri = "http://comicsdb.cz/search.php?searchfor=" + searchText;
        return loadFromUri(uri);
    }


    private static List<Series> loadFromUri(String uri) {
        List<Series> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(uri).get();
            Element table;
            int count = doc.select("table[summary=Přehled comicsů]").size();
            if (count > 1) { //jsme na stránce vyhledávání
                table = doc.select("table[summary=Přehled comicsů]").get(1);
            } else { //jsme na stránce se sériema
                table = doc.select("table[summary=Přehled comicsů]").first();
            }
            for (Element row : table.select("tbody tr")) {
                Elements columns = row.select("td");
                String title = columns.get(0).select("a").first().text();
                Integer id = Integer.parseInt(columns.get(0).select("a").first().attr("href").replaceFirst("^.*\\D", "")); //gets the id in the end of the url
                Integer numberOfComicses = Integer.parseInt(columns.get(1).select("td").text());
                Series series = new Series(title, id, numberOfComicses);
                result.add(series);
            }
        } catch (Exception e) {
        }
        return result;
    }
}
