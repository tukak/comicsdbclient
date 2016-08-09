package cz.kutner.comicsdb.fragment

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.adapter.ComicsListAdapter
import cz.kutner.comicsdb.connector.service.ComicsListService
import cz.kutner.comicsdb.di.Tracker
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.Normalizer
import javax.inject.Inject

class ComicsListFragment : AbstractFragment<Comics>() {

    @Inject lateinit var comicsListService: ComicsListService
    @Inject lateinit var tracker: Tracker

    init {
        preloadCount = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as ComicsDBApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = ComicsListAdapter(activity, data)
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
                val call = comicsListService.comicsSearch(searchText)
                call.enqueue(object : Callback<List<Comics>> {
                    override fun onResponse(call: Call<List<Comics>>?, response: Response<List<Comics>>?) {
                        if (response != null && response.isSuccessful) {
                            result = response.body()
                            showData()
                            lastPage++
                        } else {
                            switcher.showErrorView()
                        }
                    }

                    override fun onFailure(call: Call<List<Comics>>?, t: Throwable?) {
                        switcher.showErrorView()
                    }
                })
                endless = false
            } else {
                //zobrazujeme nejnovější
                val call = comicsListService.comicsList(lastPage)
                call.enqueue(object : Callback<List<Comics>> {
                    override fun onResponse(call: Call<List<Comics>>?, response: Response<List<Comics>>?) {
                        if (response != null && response.isSuccessful) {
                            result = response.body()
                            showData()
                            lastPage++
                        } else {
                            switcher.showErrorView()
                        }
                    }

                    override fun onFailure(call: Call<List<Comics>>?, t: Throwable?) {
                        switcher.showErrorView()
                    }
                })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val args = this.arguments
        if (args != null && args.containsKey(SearchManager.QUERY)) {
            (activity as AppCompatActivity).supportActionBar?.title = "Výsledek pro \"" + args.getString(SearchManager.QUERY) + "\""
            tracker.logVisit(screenName = "ComicsListFragment - Search")
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Comicsy"
            tracker.logVisit(screenName = "ComicsListFragment - List")
            Utils.logVisitToFabricAnswers(contentName = "Zobrazení seznamu comicsů", contentType = "Comics")
        }
    }

    companion object {

        fun newInstance(): ComicsListFragment {

            val args = Bundle()

            val fragment = ComicsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}


