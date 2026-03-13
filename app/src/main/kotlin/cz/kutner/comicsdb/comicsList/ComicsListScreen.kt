package cz.kutner.comicsdb.comicsList

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.comicsDetail.ComicsDetailActivity
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.ui.components.InfiniteScrollEffect
import cz.kutner.comicsdb.ui.components.ViewStateContainer

@Composable
fun ComicsListScreen(
    viewModel: ComicsListViewModel,
    onComicsClick: ((Int) -> Unit)? = null
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    val defaultClick = remember(context, onComicsClick) {
        onComicsClick ?: { comicsId: Int ->
            val intent = Intent(context, ComicsDetailActivity::class.java)
            intent.putExtra(Intent.EXTRA_UID, comicsId)
            context.startActivity(intent)
        }
    }

    LaunchedEffect(Unit) { viewModel.loadData() }
    InfiniteScrollEffect(listState, state) { viewModel.loadData() }

    ViewStateContainer(
        state = state,
        onRetry = { viewModel.loadNewData() }
    ) { comicsList ->
        LazyColumn(state = listState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(comicsList, key = { it.id }) { comics ->
                ComicsListItem(comics = comics, onClick = { defaultClick(comics.id) })
            }
        }
    }
}

@Composable
fun ComicsListItem(comics: Comics, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = comics.name.parseAsHtml().toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = comics.published, style = MaterialTheme.typography.bodyMedium)
                if (comics.rating > 0) {
                    Text(
                        text = String.format(java.util.Locale.getDefault(), "%.1f", comics.rating),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
