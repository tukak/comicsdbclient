package cz.kutner.comicsdb.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.Author;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import timber.log.Timber;

public class AuthorConverter implements Converter {

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        List<Author> result = new ArrayList<>();
        Document doc;
        Element table;
        try {
            doc = Jsoup.parse(body.in(), "windows-1250", "");
            Timber.i(doc.select("title").toString());
            if (doc.select("title").text().contentEquals("ComicsDB | vyhledávání")) {
                table = doc.select("div.search-title:contains(AUTOŘI) + table[summary=Přehled comicsů]").first();
            } else {
                table = doc.select("table[summary=Přehled comicsů]").first();
            }
            for (Element row : table.select("tbody tr")) {
                Elements columns = row.select("td");
                String name = columns.get(0).select("a").first().text();
                Integer id = Integer.parseInt(columns.get(0).select("a").first().attr("href").replaceFirst("^.*\\D", "")); //gets the id in the end of the url
                String country = columns.get(1).text();
                Author author = new Author(name, country, id);
                result.add(author);
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
