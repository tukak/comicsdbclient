package cz.kutner.comicsdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.InputStream;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by Lukáš Kutner (lukas@kutner.cz) on 11.5.2015.
 */
public class FetchComicsDetailTask extends AsyncTask<Integer, Void, Comics> {
    private String LOG_TAG = this.getClass().getSimpleName();

    @Override
    protected void onPostExecute(Comics result) {
        EventBus.getInstance().post(new ComicsDetailResultEvent(result));
    }

    @Override
    protected Comics doInBackground(Integer... params) {
        Comics comics = new Comics();
        try {
            Document doc;
            Node sibling;
            //TODO Parametrizovat cestu
            String url = "http://comicsdb.cz/comics.php?id=" + params[0].toString();
            comics.setId(params[0]);
            doc = Jsoup.connect(url).get();
            // title - H5
            String name = doc.select("H5").text();
            comics.setName(Parser.unescapeEntities(name, false));
            // rating - #rating_block - vrací Celkové hodnocení 67% (26) 1 2 3 4 5
            String rating_and_count = doc.select("#rating_block h2").first().nextSibling().toString();
            if (rating_and_count.contains("%")) {
                String rating = rating_and_count.substring(1, rating_and_count.indexOf('%'));
                comics.setRating(Integer.valueOf(rating));
                String votes = rating_and_count.substring(rating_and_count.indexOf('(') + 1, rating_and_count.indexOf(')'));
                comics.setVoteCount(Integer.valueOf(votes));
            } else {
                comics.setRating(0);
                comics.setVoteCount(0);
            }
            Elements coverElements = doc.select("img[title=Obálka]");
            if (coverElements.size() > 0) {
                String coverURI = doc.select("img[title=Obálka]").first().attr("src");
                if (!coverURI.startsWith("http")) { //občas se to vrátí bez celé adresy
                    coverURI = "http://comicsdb.cz/" + coverURI;
                }
                if (!coverURI.isEmpty()) {
                    InputStream in = new java.net.URL(coverURI).openStream();
                    Bitmap cover = BitmapFactory.decodeStream(in);
                    comics.setCover(cover);
                }
            }

            for (Element titulek : doc.select(".titulek")) {
                String title_name = titulek.text().substring(0, titulek.text().length() - 1);
                Node title_value = titulek.nextSibling();
                switch (title_name) {
                    case "Žánr":
                        String genre = title_value.toString().replaceAll("&nbsp;", " ");
                        comics.setGenre(Parser.unescapeEntities(genre.substring(1, genre.length() - 1), false));
                        break;
                    case "Vydal":
                        comics.setPublisher(Parser.unescapeEntities(Jsoup.parse(title_value.nextSibling().outerHtml()).text(), false));
                        break;
                    case "Rok a měsíc vydání":
                        comics.setPublished(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "ISSN":
                        comics.setISSN(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "Vydání":
                        comics.setIssueNumber(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "Vazba":
                        comics.setBinding(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "Format":
                        comics.setFormat(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "Počet stran":
                        comics.setPagesCount(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "Tisk":
                        comics.setPrint(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "Název originálu":
                        comics.setOriginalName(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "Vydavatel originálu":
                        comics.setOriginalPublisher(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "Cena v době vydání":
                        comics.setPrice(Parser.unescapeEntities(title_value.toString(), false));
                        break;
                    case "Popis":
                        String description = "";
                        sibling = title_value.nextSibling();
                        while (true) {
                            if (!sibling.toString().startsWith("<br")) {
                                description += sibling.toString();
                            }
                            sibling = sibling.nextSibling();
                            if (sibling.toString().startsWith("<span")) {
                                break;
                            }
                        }
                        comics.setDescription(Parser.unescapeEntities(description, false));
                        break;
                    case "Poznámky":
                        String notes = "";
                        sibling = title_value.nextSibling();
                        while (true) {
                            if (!sibling.toString().startsWith("<br")) {
                                notes += sibling.toString();
                            }
                            sibling = sibling.nextSibling();
                            if (sibling.toString().startsWith("<span")) {
                                break;
                            }
                        }
                        comics.setNotes(Parser.unescapeEntities(notes, false));
                        break;
                    case "Autoři":
                        String authors = "";
                        sibling = title_value.nextSibling();
                        authors += Jsoup.parse(sibling.outerHtml()).text();
                        authors += " ";
                        sibling = sibling.nextSibling();
                        authors += Jsoup.parse(sibling.outerHtml()).text();
                        authors += "\n";
                        sibling = sibling.nextSibling();
                        while (true) {
                            if (!sibling.toString().startsWith("<br")) {
                                if (sibling.toString().startsWith("[")) {
                                    authors += Jsoup.parse(sibling.outerHtml()).text();
                                    authors += " ";
                                    sibling = sibling.nextSibling();
                                    authors += Jsoup.parse(sibling.outerHtml()).text();
                                }
                                authors += "\n";
                            }
                            sibling = sibling.nextSibling();
                            if (sibling == null) {
                                break;
                            }
                            if (sibling.toString().startsWith("<span")) {
                                break;
                            }
                        }
                        comics.setAuthors(Parser.unescapeEntities(authors, false));
                        break;
                    case "Série":
                        comics.setSeries(Parser.unescapeEntities(Jsoup.parse(title_value.outerHtml()).text(), false));
                        break;
                }
            }
            for (Element comment : doc.select("div#prispevek")) {
                String nick = comment.select("span.prispevek-nick").first().text();
                Integer stars = comment.select("img.star").size();
                for (Element remove : comment.select("span,img")) {
                    remove.remove();
                }
                String commentText = comment.text().replace("| ", "");
                comics.addComment(new Comment(nick, stars, commentText));
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return comics;
    }
}
