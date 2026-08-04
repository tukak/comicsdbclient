package cz.kutner.comicsdb.forumList

import androidx.compose.runtime.Composable
import cz.kutner.comicsdb.model.Filter
import cz.kutner.comicsdb.ui.components.FilteredPagedListScreen
import cz.kutner.comicsdb.ui.components.UserPostCard
import cz.kutner.comicsdb.ui.components.formatDate

@Composable
fun ForumListScreen(
    viewModel: ForumListViewModel,
    filters: List<Filter>
) {
    FilteredPagedListScreen(
        viewModel = viewModel,
        filters = filters,
        key = { "${it.nick}_${it.time.time}" }
    ) { entry ->
        UserPostCard(
            nick = entry.nick,
            iconUrl = entry.iconUrl,
            subtitle = "${entry.forum} - ${formatDate(entry.time)}",
            htmlText = entry.text
        )
    }
}
