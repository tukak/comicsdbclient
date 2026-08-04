package cz.kutner.comicsdb.comicsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import cz.kutner.comicsdb.model.Comics
import cz.kutner.comicsdb.ui.components.PagedListScreen

@Composable
fun ComicsListScreen(
    viewModel: ComicsListViewModel,
    onComicsClick: (Int) -> Unit = {}
) {
    PagedListScreen(viewModel = viewModel, key = { it.id }) { comics ->
        ComicsListItem(comics = comics, onClick = { onComicsClick(comics.id) })
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
                        text = String.format(Locale.current.platformLocale, "%.1f", comics.rating),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
