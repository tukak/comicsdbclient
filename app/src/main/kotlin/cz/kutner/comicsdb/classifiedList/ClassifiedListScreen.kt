package cz.kutner.comicsdb.classifiedList

import androidx.compose.runtime.Composable
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.ui.components.FilteredPagedListScreen
import cz.kutner.comicsdb.ui.components.UserPostCard
import cz.kutner.comicsdb.ui.components.formatDate

@Composable
fun ClassifiedListScreen(
    viewModel: ClassifiedListViewModel,
    filters: List<Filter>
) {
    FilteredPagedListScreen(
        viewModel = viewModel,
        filters = filters,
        key = { "${it.nick}_${it.time.time}" }
    ) { classified ->
        UserPostCard(
            nick = classified.nick,
            iconUrl = classified.iconUrl,
            subtitle = "${classified.fixedCategory} - ${formatDate(classified.time)}",
            htmlText = classified.text
        )
    }
}
