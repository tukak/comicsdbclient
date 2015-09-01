package cz.kutner.comicsdb.connector.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.Series;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class SeriesConverter implements Converter {

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        List<Series> result = new ArrayList<>();
        Document doc;
        Element table;
        try {
            doc = Jsoup.parse(body.in(), "windows-1250", "");
            if (doc.select("title").text().contentEquals("ComicsDB | vyhledávání")) {
                table = doc.select("div.search-title:contains(SÉRIE) + table[summary=Přehled comicsů]").first();
            } else {
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

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
