package cz.kutner.comicsdb.seriesDetail

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.comicsDetail.ComicsDetailActivity
import cz.kutner.comicsdb.comicsList.ComicsListItem
import cz.kutner.comicsdb.model.SeriesDetail
import cz.kutner.comicsdb.ui.components.ViewStateContainer
import cz.kutner.comicsdb.ui.theme.HeaderTextColor

@Composable
fun SeriesDetailScreen(viewModel: SeriesDetailViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ViewStateContainer(
        state = state,
        modifier = modifier,
        onRetry = { /* detail screens load once via ID */ }
    ) { series ->
        SeriesDetailContent(
            series = series,
            onComicsClick = { comicsId ->
                val intent = Intent(context, ComicsDetailActivity::class.java)
                intent.putExtra(Intent.EXTRA_UID, comicsId)
                context.startActivity(intent)
            }
        )
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
