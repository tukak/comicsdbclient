package cz.kutner.comicsdb.connector;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.Comics;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 3.6.2015.
 */
public class ComicsListConnector {
    private static final String LOG_TAG = ComicsListConnector.class.getSimpleName();

    public static List<Comics> search(String searchText) {
        String uri = "http://comicsdb.cz/search.php?searchfor=" + searchText;
        return loadFromUri(uri);
    }


    public static List<Comics> get(int page) {
        String uri = "http://comicsdb.cz/comicslist.php" + "?str=" + page;
        return loadFromUri(uri);
    }

    private static List<Comics> loadFromUri(String uri) {
        List<Comics> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(uri).get();
            Element table = doc.select("table[summary=Přehled comicsů]").first();
            for (Element row : table.select("tbody tr")) {
                Elements columns = row.select("td");
                /* 0 <th scope="col" title="Titul comicsu">TITUL</th>
                1    <th scope="col" title="Rok vydání">ROK</th>
		        2    <th scope="col">ISBN/ISSN</th>
		        3    <th scope="col" title="Celkové hodnocení">%</th>
		        4    <th scope="col" title="Počet zobrazení">ZOB</th>
		        5    <th scope="col" title="Počet komentářů">KOM</th>
		        6    <th scope="col" title="Počet hodnocení">HOD</th>
		        7   <th scope="col" title="Datum vložení">VLOŽENO</th>
		        8    <th scope="col" title="Datum aktualizace">AKTUAL</th> */
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
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        Log.i(LOG_TAG, "Velikost v connectoru " + result.size());
        return result;
    }
}
