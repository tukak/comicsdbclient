package cz.kutner.comicsdb.authorList

import androidx.compose.foundation.clickable
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
import androidx.core.text.parseAsHtml
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.ui.components.InfiniteScrollEffect
import cz.kutner.comicsdb.ui.components.ViewStateContainer

@Composable
fun AuthorListScreen(
    viewModel: AuthorListViewModel,
    onAuthorClick: (Int) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) { viewModel.loadData() }
    InfiniteScrollEffect(listState, state) { viewModel.loadData() }

    ViewStateContainer(
        state = state,
        onRetry = { viewModel.loadNewData() }
    ) { authorList ->
        LazyColumn(state = listState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(authorList, key = { it.id }) { author ->
                AuthorListItem(author = author, onClick = { onAuthorClick(author.id) })
            }
        }
    }
}

@Composable
fun AuthorListItem(author: Author, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = author.name.parseAsHtml().toString(),
                style = MaterialTheme.typography.titleMedium
            )
            if (!author.country.isNullOrEmpty()) {
                Text(text = author.country, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
