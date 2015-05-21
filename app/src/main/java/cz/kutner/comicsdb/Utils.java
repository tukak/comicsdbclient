package cz.kutner.comicsdb;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.model.Comics;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.4.2015.
 */
public class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<Comics> getComicsListFromURL(String uri) {
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
        return result;
    }

    public static <T> T nvl(T a, T b) {
        return (a == null) ? b : a;
    }

    public static boolean isConnected(Activity activity) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) activity.getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            connected = true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            connected = true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            connected = true;
        }
        return connected;
    }

    public static Bitmap getFromCacheOrDownload(String url) throws IOException {
        //Dekodovat z base64, pokud začíná na data
        if (!url.startsWith("http") && !url.startsWith("data")) { //občas se to vrátí bez celé adresy
            url = "http://comicsdb.cz/" + url;
        }
        Bitmap result = null;
        if (url.startsWith("http")) {
            result = (Bitmap) Cache.getInstance().getLru().get(url);
            if (result == null) {
                InputStream in = new java.net.URL(url).openStream();
                result = BitmapFactory.decodeStream(in);
                Cache.getInstance().getLru().put(url, result);
            }
        } else if (url.startsWith("data")) {
            Log.i(LOG_TAG, url);
            String imageDataBytes = url.substring(url.indexOf(",") + 1);
            InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
            result = BitmapFactory.decodeStream(stream);
        }
        return result;
    }
}
