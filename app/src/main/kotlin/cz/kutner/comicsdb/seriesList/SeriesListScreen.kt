package cz.kutner.comicsdb.seriesList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.kutner.comicsdb.model.Series
import cz.kutner.comicsdb.ui.components.PagedListScreen

@Composable
fun SeriesListScreen(
    viewModel: SeriesListViewModel,
    onSeriesClick: (Int) -> Unit = {}
) {
    PagedListScreen(viewModel = viewModel, key = { it.id }) { series ->
        SeriesListItem(series = series, onClick = { onSeriesClick(series.id) })
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
