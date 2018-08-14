package cz.kutner.comicsdb.forumList

import cz.kutner.comicsdb.abstracts.AbstractPagedViewModel
import cz.kutner.comicsdb.network.RetrofitModule
import cz.kutner.comicsdb.model.ForumEntry
import kotlinx.coroutines.experimental.Deferred

class ForumListViewModel(retrofitModule: RetrofitModule) :
    AbstractPagedViewModel<ForumEntry>(retrofitModule) {
    override fun getJob(): Deferred<List<ForumEntry>?> =
        retrofitModule.forumListService.filteredForumList(start * count, count, filterId)
}