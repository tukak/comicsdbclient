package cz.kutner.comicsdb.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import cz.kutner.comicsdb.Utils;
import cz.kutner.comicsdb.event.ClassifiedResultEvent;
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdb.event.ForumResultEvent;
import cz.kutner.comicsdb.model.Classified;
import cz.kutner.comicsdb.model.ForumEntry;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class FetchClassifiedTask
        extends AsyncTask<String, Void, List<Classified>> {
    private String LOG_TAG = getClass().getSimpleName();
    private Context context;

    public FetchClassifiedTask(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected void onPostExecute(List<Classified> result) {
        EventBus.getInstance().post(new ClassifiedResultEvent(result));
    }

    @Override
    protected List<Classified> doInBackground(String... params) {
        List<Classified> result = new ArrayList<>();
        Document doc;
        try {
            String url = params[0];
            doc = Jsoup.connect(url).get();
            for (Element entry : doc.select("div#prispevek")) {
                String nick = entry.select("span.prispevek-nick").get(0).text();
                String category = entry.select("span.prispevek-nick").get(1).text();
                ;
                String time = entry.select("span.prispevek-cas").get(0).text();
                String iconUrl = entry.select("div#prispevek-icon").select("img").first().attr("src");
                for (Element remove : entry.select("span,img")) {
                    remove.remove();
                }
                String text = entry.select("div#prispevek-text").html().replace("| ", "").replace("<br></br>", "");
                Classified classified = new Classified(nick, time, category, text);
                if (!iconUrl.isEmpty()) {
                    classified.setIcon(Utils.getFromCacheOrDownload(iconUrl));
                }
                result.add(classified);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }
}

