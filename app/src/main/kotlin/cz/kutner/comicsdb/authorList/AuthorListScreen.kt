package cz.kutner.comicsdb.authorList

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
import androidx.core.text.parseAsHtml
import cz.kutner.comicsdb.model.Author
import cz.kutner.comicsdb.ui.components.PagedListScreen

@Composable
fun AuthorListScreen(
    viewModel: AuthorListViewModel,
    onAuthorClick: (Int) -> Unit = {}
) {
    PagedListScreen(viewModel = viewModel, key = { it.id }) { author ->
        AuthorListItem(author = author, onClick = { onAuthorClick(author.id) })
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
