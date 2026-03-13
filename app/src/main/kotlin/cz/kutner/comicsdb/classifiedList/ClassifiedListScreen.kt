package cz.kutner.comicsdb.classifiedList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kutner.comicsdb.model.Classified
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.ui.components.CoilImage
import cz.kutner.comicsdb.ui.components.HtmlText
import cz.kutner.comicsdb.ui.components.InfiniteScrollEffect
import cz.kutner.comicsdb.ui.components.ViewStateContainer
import cz.kutner.comicsdb.ui.components.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassifiedListScreen(
    viewModel: ClassifiedListViewModel,
    filters: List<Filter>
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    var selectedFilter by remember { mutableStateOf(filters.first()) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.loadData() }
    InfiniteScrollEffect(listState, state) { viewModel.loadData() }

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
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
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

        ViewStateContainer(
            state = state,
            onRetry = { viewModel.loadNewData() }
        ) { classifiedList ->
            LazyColumn(state = listState, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(classifiedList, key = { "${it.nick}_${it.time.time}" }) { classified ->
                    ClassifiedListItem(classified = classified)
                }
            }
        }
    }
}

@Composable
fun ClassifiedListItem(classified: Classified) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            CoilImage(
                url = classified.iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = classified.nick, style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "${classified.category} - ${formatDate(classified.time)}",
                    style = MaterialTheme.typography.bodySmall
                )
                HtmlText(
                    html = classified.text,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
