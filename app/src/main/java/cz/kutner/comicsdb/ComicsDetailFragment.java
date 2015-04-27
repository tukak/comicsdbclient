package cz.kutner.comicsdb;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.InputStream;

import cz.kutner.comicsdb.Utils.*;

import cz.kutner.comicsdbclient.comicsdbclient.R;

/**
 * Created by lukas.kutner on 31.3.2015.
 */


public class ComicsDetailFragment extends Fragment {

    private final String LOG_TAG = ComicsDetailFragment.class.getSimpleName();

    public ComicsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.comics_detail, container, false);
        Bundle args = this.getArguments();
        String url = args.getString("url");
        FetchComicsDetail task = new FetchComicsDetail(this.getActivity());
        task.execute(url);
        return rootView;
    }

    public class FetchComicsDetail extends AsyncTask<String, Void, Comics> {
        private final String LOG_TAG = FetchComicsDetail.class.getSimpleName();

        Activity myActivity;

        public FetchComicsDetail(Activity activity) {
            this.myActivity = activity;
        }


        @Override
        protected void onPostExecute(Comics result) {
            if (result != null) {
                TextView name = (TextView) myActivity.findViewById(R.id.name);
                TextView rating = (TextView) myActivity.findViewById(R.id.rating);
                TextView description = (TextView) myActivity.findViewById(R.id.description);
                TextView genre = (TextView) myActivity.findViewById(R.id.genre);
                TextView publisher = (TextView) myActivity.findViewById(R.id.publisher);
                TextView issueNumber = (TextView) myActivity.findViewById(R.id.issueNumber);
                TextView binding = (TextView) myActivity.findViewById(R.id.binding);
                TextView format = (TextView) myActivity.findViewById(R.id.format);
                TextView pagesCount = (TextView) myActivity.findViewById(R.id.pagesCount);
                TextView originalName = (TextView) myActivity.findViewById(R.id.originalName);
                TextView price = (TextView) myActivity.findViewById(R.id.price);
                TextView notes = (TextView) myActivity.findViewById(R.id.notes);
                TextView authors = (TextView) myActivity.findViewById(R.id.authors);
                TextView series = (TextView) myActivity.findViewById(R.id.series);
                TextView comments = (TextView) myActivity.findViewById(R.id.comments);
                ImageView cover = (ImageView) myActivity.findViewById(R.id.cover);
                TextView URL = (TextView) myActivity.findViewById(R.id.url);

                name.setText(result.getName());
                if (result.getRating() > 0) {
                    rating.setText(result.getRating().toString() + "% (" + result.getVoteCount().toString() + ")");
                } else {
                    rating.setText("< 5 hodnocení");
                }
                genre.setText(Utils.nvl(result.getGenre(), ""));
                publisher.setText(result.getPublisher() + " - " + result.getPublished());
                issueNumber.setText("Vydání: " + Utils.nvl(result.getIssueNumber(), "") + " tisk: " + Utils.nvl(result.getPrint(), ""));
                binding.setText("Vazba: " + Utils.nvl(result.getBinding(), ""));
                format.setText("Formát: " + Utils.nvl(result.getFormat(), ""));
                pagesCount.setText("Počet stran: " + Utils.nvl(result.getPagesCount(), ""));
                if (result.getOriginalName() != null) {
                    originalName.setText("Původně: " + result.getOriginalName());
                    if (result.getOriginalPublisher() != null) {
                        originalName.setText(originalName.getText() + " - " + result.getOriginalPublisher());
                    }
                } else {
                    originalName.setText("");
                }
                price.setText("Cena: " + Utils.nvl(result.getPrice(), ""));
                notes.setText(result.getNotes());
                description.setText(result.getDescription());
                authors.setText(result.getAuthors());
                series.setText(result.getSeries());
                cover.setImageBitmap(result.getCover());
                comments.setText("Komentáře a hodnocení:\n\n");
                for (Comment comment : result.getComments()) {
                    comments.setText(comments.getText() + comment.getNick());
                    if (comment.getStars() > 0) {
                        comments.setText(comments.getText() + " - " + comment.getStars().toString() + "/5");
                    }
                    comments.setText(comments.getText() + "\n" + comment.getText() + "\n\n");
                }
                URL.setText(result.getUrl());
            }
        }

        @Override
        protected Comics doInBackground(String... params) {
            Comics comics = new Comics();
            try {
                Document doc;
                Node sibling;
                String url = "http://comicsdb.cz/" + params[0];
                comics.setUrl(url);
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
                    InputStream in = null;
                    if (!coverURI.isEmpty()) {
                        in = new java.net.URL(coverURI).openStream();
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
                // TODO Auto-generated catch block
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            return comics;
        }
    }
}
