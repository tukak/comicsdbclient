package cz.kutner.comicsdb.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.android.gms.analytics.HitBuilders
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.adapter.SeriesDetailRVAdapter
import cz.kutner.comicsdb.model.Series
import kotlinx.android.synthetic.fragment.*
import org.jetbrains.anko.async
import org.jetbrains.anko.onClick
import org.jetbrains.anko.uiThread
import pl.aprilapps.switcher.Switcher


public class SeriesDetailFragment : Fragment() {

    private val switcher: Switcher by lazy { Switcher.Builder().withContentView(content).withEmptyView(empty_view).withProgressView(progress_view).withErrorView(error_view).build() }

    private var series: Series? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val llm = LinearLayoutManager(view?.context)
        try_again.onClick {
            if (Utils.isConnected()) {
                onResume()
            }
        }
        recycler_view.layoutManager = llm
        spinner.visibility = View.GONE
        filter_text.visibility = View.GONE
        switcher.showProgressView()
    }

    private fun loadData() {
        val id = this.arguments.getInt("id")
        async {
            series = ComicsDBApplication.seriesDetailService.seriesDetail(id)
            uiThread {
                showData()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (!Utils.isConnected()) {
            switcher.showErrorView()
        } else {
            switcher.showProgressView()
            loadData()
        }
    }


    private fun showData() {
        if (series != null) {
            var existing_series: Series = series as Series
            (activity as AppCompatActivity).supportActionBar?.title = existing_series.name
            val adapter = SeriesDetailRVAdapter(existing_series)
            recycler_view.adapter = adapter
            recycler_view.setHasFixedSize(true)
            switcher.showContentView()
            val tracker = ComicsDBApplication.tracker
            tracker.setScreenName("SeriesDetailFragment")
            tracker.send(HitBuilders.ScreenViewBuilder().build())
            tracker.send(HitBuilders.EventBuilder().setCategory("Detail").setAction(existing_series.name).build())
            Answers.getInstance().logContentView(ContentViewEvent().putContentName("Zobrazení detailu série").putContentType("Série").putContentId(existing_series.name))
        }
    }

    companion object {

        public fun newInstance(): SeriesDetailFragment {
            val args = Bundle()
            val fragment = SeriesDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
