package cz.kutner.comicsdb.forumList

import cz.kutner.comicsdb.abstracts.AbstractAndroidViewModel
import cz.kutner.comicsdb.di.RetrofitModule
import cz.kutner.comicsdb.model.ForumEntry
import kotlinx.coroutines.experimental.Deferred

class ForumListViewModel(retrofitModule: RetrofitModule) :
    AbstractAndroidViewModel<ForumEntry>(retrofitModule) {
    override fun getJob(): Deferred<List<ForumEntry>?> =
        retrofitModule.forumListService.filteredForumList(start * count, count, filterId)
}