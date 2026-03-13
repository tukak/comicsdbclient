package cz.kutner.comicsdb.search

import android.app.SearchManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import cz.kutner.comicsdb.authorList.AuthorListViewModel
import cz.kutner.comicsdb.comicsList.ComicsListViewModel
import cz.kutner.comicsdb.seriesList.SeriesListViewModel
import cz.kutner.comicsdb.ui.theme.ComicsDBTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.Normalizer

class SearchActivity : ComponentActivity() {
    private val comicsViewModel: ComicsListViewModel by viewModel()
    private val seriesViewModel: SeriesListViewModel by viewModel()
    private val authorViewModel: AuthorListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val query = intent.getStringExtra(SearchManager.QUERY) ?: ""
        val normalizedQuery = Normalizer.normalize(query, Normalizer.Form.NFD)
            .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")

        comicsViewModel.searchText = normalizedQuery
        seriesViewModel.searchText = normalizedQuery
        authorViewModel.searchText = normalizedQuery

        setContent {
            ComicsDBTheme {
                SearchScreen(
                    comicsViewModel = comicsViewModel,
                    seriesViewModel = seriesViewModel,
                    authorViewModel = authorViewModel
                )
            }
        }
    }
}
