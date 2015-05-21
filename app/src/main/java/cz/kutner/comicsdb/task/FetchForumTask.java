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
import cz.kutner.comicsdb.event.EventBus;
import cz.kutner.comicsdb.event.ForumResultEvent;
import cz.kutner.comicsdb.model.ForumEntry;
import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 21.5.2015.
 */
public class FetchForumTask
        extends AsyncTask<String, Void, List<ForumEntry>> {
    private String LOG_TAG = getClass().getSimpleName();
    private Context context;

    public FetchForumTask(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected void onPostExecute(List<ForumEntry> result) {
        EventBus.getInstance().post(new ForumResultEvent(result));
    }

    @Override
    protected List<ForumEntry> doInBackground(String... params) {
        List<ForumEntry> result = new ArrayList<>();
        Document doc;
        try {
            String url = context.getString(R.string.url_forum);
            doc = Jsoup.connect(url).get();
            for (Element entry : doc.select("div#prispevek")) {
                String nick = entry.select("span.prispevek-nick").get(0).text();
                String forum = entry.select("span.prispevek-nick").get(1).text();
                ;
                String time = entry.select("span.prispevek-cas").get(0).text();
                String iconUrl = entry.select("div#prispevek-icon").select("img").first().attr("src");
                for (Element remove : entry.select("span,img")) {
                    remove.remove();
                }
                String text = entry.select("div#prispevek-text").html().replace("| ", "").replace("<br></br>", "");
                ForumEntry forumEntry = new ForumEntry(nick, time, forum, text);
                if (!iconUrl.isEmpty()) {
                    if (!iconUrl.startsWith("http")) { //občas se to vrátí bez celé adresy
                        iconUrl = "http://comicsdb.cz/" + iconUrl;
                    }
                    forumEntry.setIcon(Utils.getFromCacheOrDownload(iconUrl));
                }
                result.add(forumEntry);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }
}

