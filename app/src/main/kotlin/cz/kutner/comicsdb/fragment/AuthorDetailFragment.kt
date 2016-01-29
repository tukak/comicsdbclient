package cz.kutner.comicsdb.fragment


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.Utils
import cz.kutner.comicsdb.adapter.AuthorDetailRVAdapter
import cz.kutner.comicsdb.model.Author
import kotlinx.android.synthetic.main.fragment.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread


public class AuthorDetailFragment : AbstractDetailFragment() {

    private var author: Author? = null

    override fun loadData() {
        val id = this.arguments.getInt("id")
        async() {
            author = ComicsDBApplication.authorDetailService.authorDetail(id)
            uiThread {
                showData()
            }
        }

    }

    override fun showData() {
        if (author != null) {
            val existing_author: Author = author as Author
            (activity as AppCompatActivity).supportActionBar?.title = existing_author.name
            val adapter = AuthorDetailRVAdapter(existing_author)
            recycler_view.adapter = adapter
            recycler_view.setHasFixedSize(true)
            switcher.showContentView()
            Utils.logVisitToGoogleAnalytics(screenName = "AuthorDetailFragment", category = "Detail", action = existing_author.name)
            Utils.logVisitToFabricAnswers(contentName = "Zobrazení detailu autora", contentType = "Autor", contentId = existing_author.name)
        }
    }

    companion object {
        public fun newInstance(): AuthorDetailFragment {
            val args = Bundle()
            val fragment = AuthorDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
