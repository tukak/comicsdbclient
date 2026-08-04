package cz.kutner.comicsdb.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.model.Item

@Composable
fun <T : Item> PagedListScreen(
    viewModel: AbstractPagedViewModel<T>,
    key: (T) -> Any,
    itemContent: @Composable (T) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pageLoadFailed by viewModel.pageLoadFailed.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) { viewModel.loadData() }
    InfiniteScrollEffect(listState, state) { viewModel.loadData() }

    LaunchedEffect(pageLoadFailed) {
        if (pageLoadFailed) {
            val result = snackbarHostState.showSnackbar(
                message = "Další položky se nepodařilo načíst",
                actionLabel = "Zkusit znovu"
            )
            if (result == SnackbarResult.ActionPerformed) viewModel.loadData()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ViewStateContainer(
            state = state,
            onRetry = { viewModel.loadNewData() }
        ) { list ->
            LazyColumn(state = listState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(list, key = key) { itemContent(it) }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Item> FilteredPagedListScreen(
    viewModel: AbstractPagedViewModel<T>,
    filters: List<Filter>,
    key: (T) -> Any,
    itemContent: @Composable (T) -> Unit
) {
    var selectedFilter by remember { mutableStateOf(filters.first()) }
    var expanded by remember { mutableStateOf(false) }

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                value = selectedFilter.title,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                filters.forEach { filter ->
                    DropdownMenuItem(
                        text = { Text(filter.title) },
                        onClick = {
                            if (selectedFilter != filter) {
                                selectedFilter = filter
                                viewModel.filterId = filter.id
                                viewModel.loadNewData()
                            }
                            expanded = false
                        }
                    )
                }
            }
        }

        PagedListScreen(viewModel = viewModel, key = key, itemContent = itemContent)
    }
}
