package comicsdb.kutner.cz;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        Log.i(LOG_TAG, "URL je " + url);
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
                TextView popis = (TextView) myActivity.findViewById(R.id.description);
                popis.setText(result.getTitle());
            }
        }

        @Override
        protected Comics doInBackground(String... params) {
            Comics comics = new Comics();
            try {
                Document doc;
                doc = Jsoup.connect("http://comicsdb.cz/" + params[0]).get();
                comics.setTitle(doc.toString());

                // title - H5
                // rating - #rating_block - vrací Celkové hodnocení 67% (26) 1 2 3 4 5
                //Document document = Jsoup.parse("<div>"
                //       + "<a href=\"#\"> I don't want this text </a>"
                //       + "**I want to retrieve this text**" + "</div>");
                //Element a = document.select("a").first();
                // Node node = a.nextSibling();
                // System.out.println(node.toString());

                //Žánr
                //Vydal:
                //Rok a měsic vydání:
                //ISSN:
                //Vydání:
                //Vazba:
                //Format:
                //Počet stran:
                //Tisk:
                //Název originálu:
                //Cena v době vydání:
                //Popis:
                //Poznámky:
                //Autoři:
                //Série:

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            return comics;
        }
    }
}
