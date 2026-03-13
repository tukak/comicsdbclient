package cz.kutner.comicsdb.newsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.model.NewsItem
import cz.kutner.comicsdb.ui.components.HtmlText
import cz.kutner.comicsdb.ui.components.InfiniteScrollEffect
import cz.kutner.comicsdb.ui.components.ViewStateContainer
import cz.kutner.comicsdb.ui.components.formatDate

@Composable
fun NewsListScreen(viewModel: NewsListViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) { viewModel.loadData() }
    InfiniteScrollEffect(listState, state) { viewModel.loadData() }

    ViewStateContainer(
        state = state,
        onRetry = { viewModel.loadNewData() }
    ) { newsList ->
        LazyColumn(state = listState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(newsList, key = { "${it.title}_${it.time.time}" }) { newsItem ->
                NewsListItem(newsItem = newsItem)
            }
        }
    }
}

@Composable
fun NewsListItem(newsItem: NewsItem) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (!newsItem.title.isNullOrEmpty()) {
                Text(text = newsItem.title, style = MaterialTheme.typography.titleMedium)
            }
            Text(
                text = "${newsItem.nick} - ${formatDate(newsItem.time)}",
                style = MaterialTheme.typography.bodySmall
            )
            HtmlText(
                html = newsItem.getTextWithUrl(),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
