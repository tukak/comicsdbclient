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
import cz.kutner.comicsdb.holder.SeriesViewHolder
import cz.kutner.comicsdb.model.Series
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import uk.co.ribot.easyadapter.EasyRecyclerAdapter
import java.text.Normalizer

public class SeriesFragment : AbstractFragment<Series>() {


    init {
        preloadCount = 20
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = EasyRecyclerAdapter(
                context,
                SeriesViewHolder::class.java,
                data as List<Any>?)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val args = this.arguments
            if (args != null && args.containsKey(SearchManager.QUERY)) {
                //neco vyhledavame
                var searchText: String = args.getString(SearchManager.QUERY)
                searchText = Normalizer.normalize(searchText, Normalizer.Form.NFD).replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
                subscription = ComicsDBApplication.seriesService.searchSeries(searchText).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { series ->
                    result = series
                    showData()
                }
                endless = false
            } else {
                //zobrazujeme nejnovější
                subscription = ComicsDBApplication.seriesService.getSeriesList(lastPage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { series ->
                    result = series
                    showData()
                }
                lastPage++
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val args = this.arguments
        val tracker = ComicsDBApplication.tracker
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
            tracker.setScreenName("SeriesFragment - Search")
            tracker.send(HitBuilders.ScreenViewBuilder().build())
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Serie"
            tracker.setScreenName("SeriesFragment - List")
            tracker.send(HitBuilders.ScreenViewBuilder().build())
            Answers.getInstance().logContentView(ContentViewEvent().putContentName("Zobrazení seznamu sérií").putContentType("Série"))
        }
    }

    companion object {

        public fun newInstance(): SeriesFragment {

            val args = Bundle()

            val fragment = SeriesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

