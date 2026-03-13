package cz.kutner.comicsdb.seriesList

import android.content.Intent
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.model.Series
import cz.kutner.comicsdb.seriesDetail.SeriesDetailActivity
import cz.kutner.comicsdb.ui.components.InfiniteScrollEffect
import cz.kutner.comicsdb.ui.components.ViewStateContainer

@Composable
fun SeriesListScreen(
    viewModel: SeriesListViewModel,
    onSeriesClick: ((Int) -> Unit)? = null
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    val defaultClick = remember(context, onSeriesClick) {
        onSeriesClick ?: { seriesId: Int ->
            val intent = Intent(context, SeriesDetailActivity::class.java)
            intent.putExtra(Intent.EXTRA_UID, seriesId)
            context.startActivity(intent)
        }
    }

    LaunchedEffect(Unit) { viewModel.loadData() }
    InfiniteScrollEffect(listState, state) { viewModel.loadData() }

    ViewStateContainer(
        state = state,
        onRetry = { viewModel.loadNewData() }
    ) { seriesList ->
        LazyColumn(state = listState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(seriesList, key = { it.id }) { series ->
                SeriesListItem(series = series, onClick = { defaultClick(series.id) })
            }
        }
    }
}

@Composable
fun SeriesListItem(series: Series, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = series.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Počet comicsů: ${series.numberOfComicses}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
