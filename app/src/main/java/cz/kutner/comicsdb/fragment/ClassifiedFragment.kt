package cz.kutner.comicsdb.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.android.gms.analytics.HitBuilders
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.connector.helper.ClassifiedHelper
import cz.kutner.comicsdb.holder.ClassifiedViewHolder
import cz.kutner.comicsdb.model.Classified
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import uk.co.ribot.easyadapter.EasyRecyclerAdapter

public class ClassifiedFragment : AbstractFragment<Classified>() {


    init {
        preloadCount = 8
        spinnerEnabled = true
        spinnerValues = arrayOf("Všechny inzeráty", "Prodám", "Koupím", "Vyměním", "Ostatní")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = EasyRecyclerAdapter(
                context,
                ClassifiedViewHolder::class.java,
                data as List<Classified>)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun loadData() {
        if (!searchRunning) {
            searchRunning = true
            val searchText = ""
            async() {
                result = ComicsDBApplication.classifiedService.filteredClassifiedList(lastPage, ClassifiedHelper.getCategoryId(filter), searchText)
                uiThread {
                    showData()
                    lastPage++
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "Bazar"
        val tracker = ComicsDBApplication.tracker
        tracker.setScreenName("ClassifiedFragment")
        tracker.send(HitBuilders.ScreenViewBuilder().build())
        Answers.getInstance().logContentView(ContentViewEvent().putContentName("Zobrazení inzerátů").putContentType("Inzerát"))
    }

    companion object {

        public fun newInstance(): ClassifiedFragment {

            val args = Bundle()

            val fragment = ClassifiedFragment()
            fragment.arguments = args
            return fragment
        }
    }


}
