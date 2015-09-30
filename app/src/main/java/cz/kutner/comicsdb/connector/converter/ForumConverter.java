package cz.kutner.comicsdb.connector.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.model.ForumEntry;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import timber.log.Timber;

public class ForumConverter implements Converter {

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        List<ForumEntry> result = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.parse(body.in(), "windows-1250", "");
            for (Element entry : doc.select("div#prispevek")) {
                String nick = entry.select("span.prispevek-nick").get(0).text();
                String forum = entry.select("span.prispevek-nick").get(1).text();
                String time = entry.select("span.prispevek-cas").get(0).text();
                String iconUrl = entry.select("div#prispevek-icon").select("img").first().attr("src");
                for (Element remove : entry.select("span,img")) {
                    remove.remove();
                }
                String text = entry.select("div#prispevek-text").html().replace("| ", "").replace("<br></br>", "");
                ForumEntry forumEntry = new ForumEntry(nick, time, forum, text);
                if (!iconUrl.isEmpty()) {
                    forumEntry.setIconUrl(Utils.INSTANCE$.fixUrl(iconUrl));
                }
                result.add(forumEntry);
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
