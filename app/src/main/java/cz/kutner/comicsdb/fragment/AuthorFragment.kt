package cz.kutner.comicsdb.fragment

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.android.gms.analytics.HitBuilders
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.holder.AuthorViewHolder
import cz.kutner.comicsdb.model.Author
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import uk.co.ribot.easyadapter.EasyRecyclerAdapter
import java.text.Normalizer

public class AuthorFragment : AbstractFragment<Author>() {

    init {
        preloadCount = 20
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = EasyRecyclerAdapter(
                context,
                AuthorViewHolder::class.java,
                data)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val args = this.arguments
            if (args != null && args.containsKey(SearchManager.QUERY)) {
                //neco vyhledavame
                var searchText: String = args.getString(SearchManager.QUERY)
                searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                subscription = ComicsDBApplication.getAuthorService().authorSearch(searchText).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { authors ->
                    result = authors
                    showData()
                }
                endless = false
            } else {
                //zobrazujeme nejnovější
                subscription = ComicsDBApplication.getAuthorService().listAuthors(lastPage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { authors ->
                    result = authors
                    showData()
                }
                lastPage++
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val args = this.arguments
        val tracker = ComicsDBApplication.getTracker()
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar!!.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
            tracker.setScreenName("AuthorFragment - Search")
            tracker.send(HitBuilders.ScreenViewBuilder().build())
        } else {
            (activity as AppCompatActivity).supportActionBar!!.title = "Autoři"
            tracker.setScreenName("AuthorFragment - List")
            tracker.send(HitBuilders.ScreenViewBuilder().build())
            Answers.getInstance().logContentView(ContentViewEvent().putContentName("Zobrazení seznamu autorů").putContentType("Autor"))
        }
    }

    companion object {

        public fun newInstance(): AuthorFragment {

            val args = Bundle()

            val fragment = AuthorFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


