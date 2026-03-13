package cz.kutner.comicsdb.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cz.kutner.comicsdb.authorList.AuthorListScreen
import cz.kutner.comicsdb.authorList.AuthorListViewModel
import cz.kutner.comicsdb.comicsList.ComicsListScreen
import cz.kutner.comicsdb.comicsList.ComicsListViewModel
import cz.kutner.comicsdb.seriesList.SeriesListScreen
import cz.kutner.comicsdb.seriesList.SeriesListViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    comicsViewModel: ComicsListViewModel,
    seriesViewModel: SeriesListViewModel,
    authorViewModel: AuthorListViewModel
) {
    val tabs = listOf("Comicsy", "Serie", "Autoři")
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> ComicsListScreen(viewModel = comicsViewModel)
                1 -> SeriesListScreen(viewModel = seriesViewModel)
                2 -> AuthorListScreen(viewModel = authorViewModel)
            }
        }
    }
}
