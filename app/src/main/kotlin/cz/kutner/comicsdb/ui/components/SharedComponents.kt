package cz.kutner.comicsdb.ui.components

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.parseAsHtml
import coil3.compose.AsyncImage
import cz.kutner.comicsdb.utils.CoilImageGetter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    message: String? = null
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message ?: "Nepodařilo se načíst data. Jste připojeni k Internetu?",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Zkusit znovu")
            }
        }
    }
}

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize())
}

@Composable
fun <T> ViewStateContainer(
    state: ViewState<T>,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
    content: @Composable (T) -> Unit
) {
    when (state) {
        is ViewState.Loading -> LoadingView(modifier)
        is ViewState.Error -> ErrorView(onRetry = onRetry, modifier = modifier, message = state.message)
        is ViewState.Empty -> EmptyView(modifier)
        is ViewState.Content -> Box(modifier = modifier.fillMaxSize()) { content(state.data) }
    }
}

@Composable
fun CoilImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    if (url.isNotEmpty()) {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}

@Composable
fun HtmlText(
    html: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
                isClickable = true
            }
        },
        update = { textView ->
            if (textView.tag != html) {
                textView.tag = html
                val imageGetter = CoilImageGetter(textView)
                textView.text = html.parseAsHtml(imageGetter = imageGetter)
            }
        }
    )
}

@Composable
fun InfiniteScrollEffect(
    listState: LazyListState,
    state: ViewState<List<*>>,
    preloadCount: Int = 20,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore by remember(state) {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = listState.layoutInfo.totalItemsCount
            state is ViewState.Content && totalItems > 0 && lastVisibleItem >= totalItems - preloadCount
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onLoadMore()
    }
}

fun formatDate(date: Date): String =
    SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(date)
