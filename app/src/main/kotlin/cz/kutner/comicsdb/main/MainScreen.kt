package cz.kutner.comicsdb.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.kutner.comicsdb.about.AboutScreen
import cz.kutner.comicsdb.authorList.AuthorListScreen
import cz.kutner.comicsdb.authorList.AuthorListViewModel
import cz.kutner.comicsdb.classifiedList.ClassifiedListScreen
import cz.kutner.comicsdb.classifiedList.ClassifiedListViewModel
import cz.kutner.comicsdb.comicsList.ComicsListScreen
import cz.kutner.comicsdb.comicsList.ComicsListViewModel
import cz.kutner.comicsdb.forumList.ForumListScreen
import cz.kutner.comicsdb.forumList.ForumListViewModel
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.navigation.AuthorDetailRoute
import cz.kutner.comicsdb.navigation.ComicsDetailRoute
import cz.kutner.comicsdb.navigation.SearchRoute
import cz.kutner.comicsdb.navigation.SeriesDetailRoute
import cz.kutner.comicsdb.newsList.NewsListScreen
import cz.kutner.comicsdb.newsList.NewsListViewModel
import cz.kutner.comicsdb.seriesList.SeriesListScreen
import cz.kutner.comicsdb.seriesList.SeriesListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private val classifiedFilters = listOf(
    Filter(0, "Všechny inzeráty"),
    Filter(1, "Prodám"),
    Filter(2, "Koupím"),
    Filter(3, "Vyměním"),
    Filter(10, "Ostatní")
)

private val forumFilters = listOf(
    Filter(0, "Všechna fora"),
    Filter(1, "* Připomínky a návrhy"),
    Filter(13, "Art"),
    Filter(10, "Fabula Rasa"),
    Filter(5, "Filmový klub"),
    Filter(3, "Pindárna"),
    Filter(4, "Povinná četba"),
    Filter(9, "Poznej comics nebo postavu"),
    Filter(12, "Publicistika"),
    Filter(6, "Sběratelský klub"),
    Filter(11, "Slevy, výprodeje, bazary"),
    Filter(8, "Srazy, cony, festivaly"),
    Filter(7, "Stripy, jouky, fejky :)")
)

enum class Screen(val title: String, val icon: ImageVector) {
    Comics("Comicsy", Icons.AutoMirrored.Filled.LibraryBooks),
    News("Novinky", Icons.Default.Newspaper),
    Series("Serie", Icons.AutoMirrored.Filled.Comment),
    Authors("Autoři", Icons.Default.Edit),
    Classified("Bazar", Icons.Default.Payments),
    Forum("Forum", Icons.Default.Forum),
    About("O aplikaci", Icons.Default.Info)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf(Screen.Comics) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "ComicsDB",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Screen.entries.forEach { screen ->
                    NavigationDrawerItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        onClick = {
                            currentScreen = screen
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen.title) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        if (currentScreen != Screen.About) {
                            IconButton(onClick = {
                                navController.navigate(SearchRoute())
                            }) {
                                Icon(Icons.Default.Search, contentDescription = "Hledat")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (currentScreen) {
                    Screen.Comics -> {
                        val vm = koinViewModel<ComicsListViewModel>()
                        ComicsListScreen(
                            viewModel = vm,
                            onComicsClick = { id -> navController.navigate(ComicsDetailRoute(id)) }
                        )
                    }
                    Screen.News -> {
                        val vm = koinViewModel<NewsListViewModel>()
                        NewsListScreen(viewModel = vm)
                    }
                    Screen.Series -> {
                        val vm = koinViewModel<SeriesListViewModel>()
                        SeriesListScreen(
                            viewModel = vm,
                            onSeriesClick = { id -> navController.navigate(SeriesDetailRoute(id)) }
                        )
                    }
                    Screen.Authors -> {
                        val vm = koinViewModel<AuthorListViewModel>()
                        AuthorListScreen(
                            viewModel = vm,
                            onAuthorClick = { id -> navController.navigate(AuthorDetailRoute(id)) }
                        )
                    }
                    Screen.Classified -> {
                        val vm = koinViewModel<ClassifiedListViewModel>()
                        ClassifiedListScreen(viewModel = vm, filters = classifiedFilters)
                    }
                    Screen.Forum -> {
                        val vm = koinViewModel<ForumListViewModel>()
                        ForumListScreen(viewModel = vm, filters = forumFilters)
                    }
                    Screen.About -> AboutScreen()
                }
            }
        }
    }
}
