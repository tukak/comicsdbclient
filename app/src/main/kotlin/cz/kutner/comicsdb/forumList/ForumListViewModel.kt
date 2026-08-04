package cz.kutner.comicsdb.forumList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.ForumEntry

class ForumListViewModel(private val service: ForumListService) :
    AbstractPagedViewModel<ForumEntry>() {
    override suspend fun getJob(): List<ForumEntry> =
        service.filteredForumList(start * count, count, filterId)
}
