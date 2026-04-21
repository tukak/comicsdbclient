package cz.kutner.comicsdb.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.text.parseAsHtml
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.ui.components.ViewState
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import cz.kutner.comicsdb.authorDetail.AuthorDetailScreen
import cz.kutner.comicsdb.authorDetail.AuthorDetailViewModel
import cz.kutner.comicsdb.authorList.AuthorListViewModel
import cz.kutner.comicsdb.comicsDetail.ComicsDetailScreen
import cz.kutner.comicsdb.comicsDetail.ComicsDetailViewModel
import cz.kutner.comicsdb.comicsList.ComicsListViewModel
import cz.kutner.comicsdb.image.ImageViewerScreen
import cz.kutner.comicsdb.main.MainScreen
import cz.kutner.comicsdb.search.SearchScreen
import cz.kutner.comicsdb.seriesDetail.SeriesDetailScreen
import cz.kutner.comicsdb.seriesDetail.SeriesDetailViewModel
import cz.kutner.comicsdb.seriesList.SeriesListViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import java.text.Normalizer

private val DIACRITICS_REGEX = "[\\p{InCombiningDiacriticalMarks}]".toRegex()

private inline fun <reified T : Any> deepLinksFor(vararg paths: String): List<NavDeepLink> =
    paths.flatMap { path ->
        listOf("https://comicsdb.cz", "http://comicsdb.cz", "https://www.comicsdb.cz", "http://www.comicsdb.cz")
            .map { host -> navDeepLink<T>(basePath = "$host$path") }
    }

private fun removeDiacritics(text: String): String =
    Normalizer.normalize(text, Normalizer.Form.NFD).replace(DIACRITICS_REGEX, "")

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainRoute) {
        composable<MainRoute> {
            MainScreen(navController = navController)
        }

        composable<ComicsDetailRoute>(
            deepLinks = deepLinksFor<ComicsDetailRoute>("/comics")
        ) {
            val route = it.toRoute<ComicsDetailRoute>()
            ComicsDetailContent(id = route.id, navController = navController)
        }

        composable<SeriesDetailRoute>(
            deepLinks = deepLinksFor<SeriesDetailRoute>("/serie")
        ) {
            val route = it.toRoute<SeriesDetailRoute>()
            SeriesDetailContent(id = route.id, navController = navController)
        }

        composable<AuthorDetailRoute>(
            deepLinks = deepLinksFor<AuthorDetailRoute>("/author", "/autor")
        ) {
            val route = it.toRoute<AuthorDetailRoute>()
            AuthorDetailContent(id = route.id, navController = navController)
        }

        composable<SearchRoute> {
            val route = it.toRoute<SearchRoute>()
            SearchContent(query = route.query, navController = navController)
        }

        composable<ImageViewerRoute> {
            val route = it.toRoute<ImageViewerRoute>()
            val images = remember { ImageCache.images.also { ImageCache.images = emptyList() } }
            ImageViewerScreen(
                images = images,
                initialPosition = route.position,
                onClose = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScaffold(
    navController: NavController,
    title: @Composable () -> Unit,
    subtitle: (@Composable () -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumFlexibleTopAppBar(
                title = title,
                subtitle = subtitle,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zpět")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        content(Modifier.padding(paddingValues))
    }
}

@Composable
private fun <T : Any> entityTitle(state: ViewState<T>, nameOf: (T) -> String, fallback: String): String {
    val content = state as? ViewState.Content
    return content?.data?.let { nameOf(it).parseAsHtml().toString() } ?: fallback
}

@Composable
private fun ComicsDetailContent(id: Int, navController: NavController) {
    val model: ComicsDetailViewModel = koinViewModel()
    LaunchedEffect(id) { model.loadData(id) }
    val state by model.state.collectAsStateWithLifecycle()
    val titleText = entityTitle(state, { it.name }, "Detail comicsu")

    DetailScaffold(
        navController = navController,
        title = { Text(titleText, maxLines = 2, overflow = TextOverflow.Ellipsis) }
    ) { modifier ->
        ComicsDetailScreen(
            viewModel = model,
            modifier = modifier,
            onNavigateToAuthor = { authorId -> navController.navigate(AuthorDetailRoute(authorId)) },
            onNavigateToSeries = { seriesId -> navController.navigate(SeriesDetailRoute(seriesId)) },
            onNavigateToImages = { images, position ->
                ImageCache.images = images
                navController.navigate(ImageViewerRoute(position))
            }
        )
    }
}

@Composable
private fun SeriesDetailContent(id: Int, navController: NavController) {
    val model: SeriesDetailViewModel = koinViewModel()
    LaunchedEffect(id) { model.loadData(id) }
    val state by model.state.collectAsStateWithLifecycle()
    val titleText = entityTitle(state, { it.name }, "Detail serie")

    DetailScaffold(
        navController = navController,
        title = { Text(titleText, maxLines = 2, overflow = TextOverflow.Ellipsis) }
    ) { modifier ->
        SeriesDetailScreen(
            viewModel = model,
            modifier = modifier,
            onComicsClick = { comicsId -> navController.navigate(ComicsDetailRoute(comicsId)) }
        )
    }
}

@Composable
private fun AuthorDetailContent(id: Int, navController: NavController) {
    val model: AuthorDetailViewModel = koinViewModel()
    LaunchedEffect(id) { model.loadData(id) }
    val state by model.state.collectAsStateWithLifecycle()
    val titleText = entityTitle(state, { it.name }, "Detail autora")

    DetailScaffold(
        navController = navController,
        title = { Text(titleText, maxLines = 2, overflow = TextOverflow.Ellipsis) }
    ) { modifier ->
        AuthorDetailScreen(
            viewModel = model,
            modifier = modifier,
            onComicsClick = { comicsId -> navController.navigate(ComicsDetailRoute(comicsId)) }
        )
    }
}

@Composable
private fun SearchContent(query: String, navController: NavController) {
    var searchText by rememberSaveable { mutableStateOf(query) }
    var debouncedQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(searchText) {
        delay(300)
        debouncedQuery = removeDiacritics(searchText)
    }

    val comicsViewModel: ComicsListViewModel = koinViewModel()
    val seriesViewModel: SeriesListViewModel = koinViewModel()
    val authorViewModel: AuthorListViewModel = koinViewModel()

    LaunchedEffect(debouncedQuery) {
        if (debouncedQuery.isNotEmpty()) {
            comicsViewModel.searchText = debouncedQuery
            comicsViewModel.loadNewData()
            seriesViewModel.searchText = debouncedQuery
            seriesViewModel.loadNewData()
            authorViewModel.searchText = debouncedQuery
            authorViewModel.loadNewData()
        }
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    DetailScaffold(
        navController = navController,
        title = {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Hledaný text", color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { /* debounce handles it */ }),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
        }
    ) { modifier ->
        Box(modifier = modifier) {
            if (debouncedQuery.isNotEmpty()) {
                SearchScreen(
                    comicsViewModel = comicsViewModel,
                    seriesViewModel = seriesViewModel,
                    authorViewModel = authorViewModel,
                    onComicsClick = { id -> navController.navigate(ComicsDetailRoute(id)) },
                    onSeriesClick = { id -> navController.navigate(SeriesDetailRoute(id)) },
                    onAuthorClick = { id -> navController.navigate(AuthorDetailRoute(id)) }
                )
            }
        }
    }
}
