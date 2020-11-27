package cz.kutner.comicsdb.forumList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.model.ForumEntry
import cz.kutner.comicsdb.network.RetrofitModule

class ForumListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<ForumEntry>(retrofitModule) {
    override suspend fun getJob(): List<ForumEntry> =
        retrofitModule.forumListService.filteredForumList(start * count, count, filterId)
}