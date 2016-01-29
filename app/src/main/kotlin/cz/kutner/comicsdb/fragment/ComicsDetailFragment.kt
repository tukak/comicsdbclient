package cz.kutner.comicsdb.fragment


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.adapter.ComicsDetailRVAdapter
import cz.kutner.comicsdb.model.Comics
import kotlinx.android.synthetic.main.fragment.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

public class ComicsDetailFragment : AbstractDetailFragment() {

    private var comics: Comics? = null

    override fun loadData() {
        val id = this.arguments.getInt("id")
        async() {
            comics = ComicsDBApplication.comicsService.getComics(id)
            uiThread {
                showData()
            }
        }
    }

    override fun showData() {
        if (comics != null) {
            val existing_comics: Comics = comics as Comics
            (activity as AppCompatActivity).supportActionBar?.title = existing_comics.name
            val adapter = ComicsDetailRVAdapter(existing_comics)
            recycler_view.adapter = adapter
            recycler_view.setHasFixedSize(true)
            switcher.showContentView()
            Utils.logVisitToGoogleAnalytics(screenName = "ComicsDetailFragment", category = "Detail", action = existing_comics.name)
            Utils.logVisitToFabricAnswers(contentName = "Zobrazení detailu komiksu", contentType = "Comics", contentId = existing_comics.name)
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
