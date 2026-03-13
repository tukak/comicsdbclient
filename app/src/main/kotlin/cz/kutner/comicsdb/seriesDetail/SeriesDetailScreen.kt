package cz.kutner.comicsdb.seriesDetail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.comicsList.ComicsListItem
import cz.kutner.comicsdb.model.SeriesDetail
import cz.kutner.comicsdb.ui.components.ViewStateContainer
import cz.kutner.comicsdb.ui.theme.HeaderTextColor

@Composable
fun SeriesDetailScreen(
    viewModel: SeriesDetailViewModel,
    modifier: Modifier = Modifier,
    onComicsClick: (Int) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ViewStateContainer(
        state = state,
        modifier = modifier
    ) { series ->
        SeriesDetailContent(series = series, onComicsClick = onComicsClick)
    }
}

@Composable
fun SeriesDetailContent(series: SeriesDetail, onComicsClick: (Int) -> Unit) {
    LazyColumn {
        item {
            Text(
                text = series.name.parseAsHtml().toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = HeaderTextColor,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(series.comicses, key = { it.id }) { comics ->
            ComicsListItem(comics = comics, onClick = { onComicsClick(comics.id) })
        }
    }
}
