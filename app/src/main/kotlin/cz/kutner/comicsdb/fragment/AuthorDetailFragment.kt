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


class AuthorDetailFragment : AbstractDetailFragment() {

    private lateinit var author: Author

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
        (activity as AppCompatActivity).supportActionBar?.title = author.name
        val adapter = AuthorDetailRVAdapter(author)
        recycler_view.adapter = adapter
        recycler_view.setHasFixedSize(true)
        switcher.showContentView()
        Utils.logVisitToGoogleAnalytics(screenName = "AuthorDetailFragment", category = "Detail", action = author.name)
        Utils.logVisitToFabricAnswers(contentName = "Zobrazení detailu autora", contentType = "Autor", contentId = author.name)
    }

    companion object {
        fun newInstance(): AuthorDetailFragment {
            val args = Bundle()
            val fragment = AuthorDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
