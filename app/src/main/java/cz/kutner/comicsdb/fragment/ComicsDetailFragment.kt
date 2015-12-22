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
import cz.kutner.comicsdb.adapter.ComicsDetailRVAdapter
import cz.kutner.comicsdb.model.Comics
import kotlinx.android.synthetic.main.fragment.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_progress.*
import org.jetbrains.anko.async
import org.jetbrains.anko.onClick
import org.jetbrains.anko.uiThread
import pl.aprilapps.switcher.Switcher

public class ComicsDetailFragment : Fragment() {

    private val switcher: Switcher by lazy { Switcher.Builder(ComicsDBApplication.context).addContentView(content).addEmptyView(empty_view).addProgressView(progress_view).addErrorView(error_view).build() }

    private var comics: Comics? = null

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
        async() {
            comics = ComicsDBApplication.comicsService.getComics(id)
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
        if (comics != null) {
            val existing_comics: Comics = comics as Comics
            (activity as AppCompatActivity).supportActionBar?.title = existing_comics.name
            val adapter = ComicsDetailRVAdapter(existing_comics)
            recycler_view.adapter = adapter
            recycler_view.setHasFixedSize(true)
            switcher.showContentView()
            val tracker = ComicsDBApplication.tracker
            tracker.setScreenName("ComicsDetailFragment")
            tracker.send(HitBuilders.ScreenViewBuilder().build())
            tracker.send(HitBuilders.EventBuilder().setCategory("Detail").setAction(existing_comics.name).build())
            Answers.getInstance().logContentView(ContentViewEvent().putContentName("Zobrazení detailu komiksu").putContentType("Comics").putContentId(existing_comics.name))
        }
    }

    companion object {

        public fun newInstance(): ComicsDetailFragment {
            val args = Bundle()
            val fragment = ComicsDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
