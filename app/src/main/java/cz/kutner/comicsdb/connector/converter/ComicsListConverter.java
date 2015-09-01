package cz.kutner.comicsdb.connector.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.Comics;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class ComicsListConverter implements Converter {

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        List<Comics> result = new ArrayList<>();
        Document doc;
        Element table;
        try {
            doc = Jsoup.parse(body.in(), "windows-1250", "");
            if (doc.select("title").text().contentEquals("ComicsDB | vyhledávání")) {
                table = doc.select("div.search-title:contains(COMICSY) + table[summary=Přehled comicsů]").first();
            } else {
                table = doc.select("table[summary=Přehled comicsů]").first();
            }
            for (Element row : table.select("tbody tr")) {
                Elements columns = row.select("td");
                String title = columns.get(0).select("a").first().text();
                Integer id = Integer.parseInt(columns.get(0).select("a").first().attr("href").replaceFirst("^.*\\D", "")); //gets the id in the end of the url
                String year = columns.get(1).text();
                String rating = columns.get(3).text();
                if (rating.isEmpty()) {
                    rating = "0";
                }
                Comics comics = new Comics(title, id);
                comics.setPublished(year);
                comics.setRating(Integer.valueOf(rating));
                result.add(comics);
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
