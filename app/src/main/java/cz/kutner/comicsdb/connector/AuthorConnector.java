package cz.kutner.comicsdb.connector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.Author;

public class AuthorConnector {


    public static List<Author> get(int page) {
        String uri = "http://comicsdb.cz/autorlist.php" + "?str=" + page;
        return loadFromUri(uri);
    }


    private static List<Author> loadFromUri(String uri) {
        List<Author> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(uri).get();
            Element table = doc.select("table[summary=Přehled comicsů]").first();
            for (Element row : table.select("tbody tr")) {
                Elements columns = row.select("td");
                String name = columns.get(0).select("a").first().text();
                Integer id = Integer.parseInt(columns.get(0).select("a").first().attr("href").replaceFirst("^.*\\D", "")); //gets the id in the end of the url
                String country = columns.get(1).text();
                Author author = new Author(name, country, id);
                result.add(author);
            }
        } catch (Exception e) {
        }
        return result;
    }
}
