package cz.kutner.comicsdb.connector.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.model.Classified;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class ClassifiedConverter implements Converter {
    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        List<Classified> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.parse(body.in(), "windows-1250", "");
            for (Element entry : doc.select("div#prispevek")) {
                String nick = entry.select("span.prispevek-nick").get(0).text();
                String category = entry.select("span.prispevek-nick").get(1).text();
                String time = entry.select("span.prispevek-cas").get(0).text();
                String iconUrl = entry.select("div#prispevek-icon").select("img").first().attr("src");
                for (Element remove : entry.select("span,img")) {
                    remove.remove();
                }
                String text = entry.select("div#prispevek-text").html().replace("| ", "").replace("<br></br>", "");
                Classified classified = new Classified(nick, time, category, text);
                if (!iconUrl.isEmpty()) {
                    classified.setIconUrl(Utils.fixUrl(iconUrl));
                }
                result.add(classified);
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
