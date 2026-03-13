package cz.kutner.comicsdb.authorDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import cz.kutner.comicsdb.model.AuthorDetail
import cz.kutner.comicsdb.ui.components.CoilImage
import cz.kutner.comicsdb.ui.components.HtmlText
import cz.kutner.comicsdb.ui.components.ViewStateContainer
import cz.kutner.comicsdb.ui.theme.HeaderTextColor

@Composable
fun AuthorDetailScreen(
    viewModel: AuthorDetailViewModel,
    modifier: Modifier = Modifier,
    onComicsClick: (Int) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ViewStateContainer(
        state = state,
        modifier = modifier
    ) { author ->
        AuthorDetailContent(author = author, onComicsClick = onComicsClick)
    }
}

@Composable
fun AuthorDetailContent(author: AuthorDetail, onComicsClick: (Int) -> Unit) {
    LazyColumn {
        item {
            Row(modifier = Modifier.padding(8.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = author.name.parseAsHtml().toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = HeaderTextColor,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    if (!author.country.isNullOrEmpty()) {
                        Text(
                            text = author.country,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    val bio = author.getBioFromHtml()
                    if (bio != null && bio.isNotEmpty()) {
                        HtmlText(
                            html = bio.toString(),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    val notes = author.getNotesFromHtml()
                    if (notes != null && notes.isNotEmpty()) {
                        HtmlText(
                            html = notes.toString(),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                if (author.photoUrl.isNotEmpty()) {
                    CoilImage(
                        url = author.photoUrl,
                        contentDescription = "Fotka autora",
                        modifier = Modifier
                            .width(80.dp)
                            .height(123.dp)
                            .padding(start = 8.dp)
                    )
                }
            }
        }
        items(author.comicses, key = { it.id }) { comics ->
            ComicsListItem(comics = comics, onClick = { onComicsClick(comics.id) })
        }
    }
}
