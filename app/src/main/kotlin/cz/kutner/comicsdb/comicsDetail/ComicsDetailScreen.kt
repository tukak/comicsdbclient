package cz.kutner.comicsdb.comicsDetail

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.authorDetail.AuthorDetailActivity
import cz.kutner.comicsdb.image.ImageViewSliderActivity
import cz.kutner.comicsdb.model.Comment
import cz.kutner.comicsdb.model.ComicsDetail
import cz.kutner.comicsdb.seriesDetail.SeriesDetailActivity
import cz.kutner.comicsdb.ui.components.CoilImage
import cz.kutner.comicsdb.ui.components.HtmlText
import cz.kutner.comicsdb.ui.components.ViewStateContainer
import cz.kutner.comicsdb.ui.components.formatDate
import cz.kutner.comicsdb.ui.theme.HeaderTextColor

@Composable
fun ComicsDetailScreen(viewModel: ComicsDetailViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ViewStateContainer(
        state = state,
        modifier = modifier,
        onRetry = { /* detail screens load once via ID */ }
    ) { comics ->
        ComicsDetailContent(comics = comics)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ComicsDetailContent(comics: ComicsDetail) {
    val context = LocalContext.current

    val allImages = remember(comics) { arrayListOf(comics.cover).apply { addAll(comics.samples) } }

    LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
        // Title
        item {
            Text(
                text = comics.name.parseAsHtml().toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = HeaderTextColor,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        // Cover + rating + info
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                CoilImage(
                    url = comics.cover.previewUrl,
                    contentDescription = "Obálka",
                    modifier = Modifier
                        .width(200.dp)
                        .height(300.dp)
                        .clickable {
                            val intent = Intent(context, ImageViewSliderActivity::class.java)
                            intent.putParcelableArrayListExtra(ImageViewSliderActivity.IMAGES, allImages)
                            intent.putExtra(ImageViewSliderActivity.POSTITION, 0)
                            context.startActivity(intent)
                        }
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    // Rating
                    if (comics.rating > 0) {
                        Text(
                            text = "${String.format(java.util.Locale.getDefault(), "%.1f", comics.rating)} (${comics.voteCount} hlasů)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        Text(text = "< 5 hodnocení", style = MaterialTheme.typography.bodyMedium)
                    }
                    // Info fields
                    if (comics.genre.isNotEmpty()) Text(text = comics.genre, style = MaterialTheme.typography.bodySmall)
                    if (comics.publisher.isNotEmpty()) Text(text = "${comics.publisher} ${comics.published}", style = MaterialTheme.typography.bodySmall)
                    if (comics.pagesCount.isNotEmpty()) Text(text = "Počet stran: ${comics.pagesCount}", style = MaterialTheme.typography.bodySmall)
                    if (comics.price.isNotEmpty()) Text(text = "Cena: ${comics.price}", style = MaterialTheme.typography.bodySmall)
                    val originals = comics.getOriginals()
                    if (originals.isNotEmpty()) Text(text = originals.toString(), style = MaterialTheme.typography.bodySmall)
                    if (comics.binding.isNotEmpty()) Text(text = "Vazba: ${comics.binding}", style = MaterialTheme.typography.bodySmall)

                    // Clickable series
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = MaterialTheme.colorScheme.primary)) {
                                append(comics.series.name)
                            }
                        },
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.clickable {
                            val intent = Intent(context, SeriesDetailActivity::class.java)
                            intent.putExtra(Intent.EXTRA_UID, comics.series.id)
                            context.startActivity(intent)
                        }
                    )

                    if (comics.issueNumber.isNotEmpty() || comics.print.isNotEmpty()) {
                        Text(text = "Vydání: ${comics.issueNumber} tisk: ${comics.print}", style = MaterialTheme.typography.bodySmall)
                    }
                    if (comics.format.isNotEmpty()) Text(text = "Formát: ${comics.format}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Sample images
        if (comics.samples.isNotEmpty()) {
            item {
                FlowRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    comics.samples.forEachIndexed { index, sample ->
                        CoilImage(
                            url = sample.previewUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .width(40.dp)
                                .height(60.dp)
                                .clickable {
                                    val intent = Intent(context, ImageViewSliderActivity::class.java)
                                    intent.putParcelableArrayListExtra(ImageViewSliderActivity.IMAGES, allImages)
                                    intent.putExtra(ImageViewSliderActivity.POSTITION, index + 1)
                                    context.startActivity(intent)
                                }
                        )
                    }
                }
            }
        }

        // Description
        item {
            val desc = comics.getDescriptionFromHtml()
            if (desc.isNotEmpty()) {
                Text(
                    text = desc.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Notes
        item {
            val notes = comics.getNotesFromHtml()
            if (notes.isNotEmpty()) {
                Text(
                    text = notes.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Authors
        item {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                for (author in comics.authors) {
                    Row {
                        Text(text = "${author.role} ", style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = MaterialTheme.colorScheme.primary)) {
                                    append(author.name.parseAsHtml().toString())
                                }
                            },
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.clickable {
                                val intent = Intent(context, AuthorDetailActivity::class.java)
                                intent.putExtra(Intent.EXTRA_UID, author.id)
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
        }

        // Comments
        items(comics.comments, key = { "${it.nick}_${it.time.time}" }) { comment ->
            CommentItem(comment = comment)
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            CoilImage(
                url = comment.iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = comment.nick, style = MaterialTheme.typography.titleSmall)
                Text(
                    text = formatDate(comment.time),
                    style = MaterialTheme.typography.bodySmall
                )
                if (comment.stars > 0) {
                    Text(
                        text = "\u2605".repeat(comment.stars),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                HtmlText(
                    html = comment.text,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
