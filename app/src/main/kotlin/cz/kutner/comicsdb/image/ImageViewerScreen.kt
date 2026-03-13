package cz.kutner.comicsdb.image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import me.saket.telephoto.zoomable.coil3.ZoomableAsyncImage
import cz.kutner.comicsdb.model.Image
import kotlinx.coroutines.launch

@Composable
fun ImageViewerScreen(
    images: List<Image>,
    initialPosition: Int = 0
) {
    val pagerState = rememberPagerState(initialPage = initialPosition) { images.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        if (images.size > 1) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth()
            ) {
                images.forEachIndexed { index, image ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        text = { Text(image.caption) }
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ZoomableAsyncImage(
                model = images[page].fullUrl,
                contentDescription = images[page].caption,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
